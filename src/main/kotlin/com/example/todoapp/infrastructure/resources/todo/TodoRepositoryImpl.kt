package com.example.todoapp.infrastructure.resources.todo

import com.example.todoapp.domain.exceptions.ULIDNotFoundException
import com.example.todoapp.domain.resources.base.ULID
import com.example.todoapp.domain.resources.todo.*
import com.example.todoapp.infrastructure.exposed.tables.Todos
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class TodoRepositoryImpl : TodoRepository {
    private fun findRowById(id: ULID): ResultRow? {
        return Todos.select { Todos.id eq id }.firstOrNull()
    }

    override fun create(args: TodoCreateArgs): Todo {
        val now = Instant.now()
        val id = Todos.insertAndGetId {
            it[title] = args.title
            it[completed] = false
            it[createdAt] = now
            it[updatedAt] = now
        }
        val row = findRowById(id.value) ?: throw ULIDNotFoundException(id.value)
        return toDomain(row)
    }

    override fun update(args: TodoUpdateArgs): Todo {
        val now = Instant.now()
        Todos.update({ Todos.id eq args.id }) {
            args.title?.let { value -> it[title] = value }
            args.completed?.let { value -> it[completed] = value }
            it[updatedAt] = now
        }
        val row = findRowById(args.id) ?: throw ULIDNotFoundException(args.id)
        return toDomain(row)
    }

    override fun delete(args: TodoDeleteArgs): Boolean {
        findRowById(args.id) ?: throw ULIDNotFoundException(args.id)
        return Todos.deleteWhere { Todos.id eq args.id } > 0
    }

    override fun findById(id: ULID): Todo {
        val row = findRowById(id) ?: throw ULIDNotFoundException(id)
        return toDomain(row)
    }

    override fun findAll(args: TodoFindAllArgs): List<Todo> {
        val query = Todos.selectAll()
        args.title?.let {
            query.andWhere { Todos.title like "%${args.title}%" }
        }
        args.completed?.let {
            query.andWhere { Todos.completed eq args.completed }
        }

        return query.toList().map(this::toDomain)
    }

    private fun toDomain(row: ResultRow): Todo {
        return Todo(
            id = row[Todos.id].value,
            title = row[Todos.title],
            completed = row[Todos.completed],
            createdAt = row[Todos.createdAt],
            updatedAt = row[Todos.updatedAt],
        )
    }
}