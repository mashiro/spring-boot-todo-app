package com.example.todoapp.infrastructure.resources.todo

import assertk.assertThat
import assertk.assertions.*
import com.example.todoapp.domain.exceptions.ULIDNotFoundException
import com.example.todoapp.domain.resources.base.SortOrder
import com.example.todoapp.domain.resources.base.ulid
import com.example.todoapp.domain.resources.todo.*
import com.example.todoapp.infrastructure.exposed.tables.Todos
import com.example.todoapp.infrastructure.testcontainers.KMySQLContainer
import com.example.todoapp.infrastructure.testcontainers.MySQLContainerExtension
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
@ExtendWith(MySQLContainerExtension::class)
internal class TodoRepositoryImplTest {
    private val todoRepositoryImpl = TodoRepositoryImpl()

    @Test
    fun containerResolver(mysql: KMySQLContainer) {
        assertThat(mysql.isRunning).isTrue()
    }

    @Test
    fun create() {
        val todo = todoRepositoryImpl.create(TodoCreateArgs(title = "test"))
        assertThat(todo.id).isNotNull()
        assertThat(todo.title).isEqualTo("test")
        assertThat(todo.completed).isFalse()
    }

    @Test
    fun update() {
        val now = Instant.now()
        val id = Todos.insertAndGetId {
            it[title] = "test"
            it[completed] = false
            it[createdAt] = now
            it[updatedAt] = now
        }

        val args = TodoUpdateArgs(id = id.value, completed = true)
        val todo = todoRepositoryImpl.update(args)
        assertThat(todo.id).isEqualTo(id.value)
        assertThat(todo.title).isEqualTo("test")
        assertThat(todo.completed).isEqualTo(true)
        assertThat(todo.createdAt).isEqualTo(now)
        assertThat(todo.updatedAt).isGreaterThan(now)
    }

    @Test
    fun updateWithNonExistId() {
        assertThat {
            val args = TodoUpdateArgs(id = ulid())
            todoRepositoryImpl.update(args)
        }.isFailure().isInstanceOf(ULIDNotFoundException::class)
    }

    @Test
    fun delete() {
        val now = Instant.now()
        val id = Todos.insertAndGetId {
            it[title] = "test"
            it[completed] = false
            it[createdAt] = now
            it[updatedAt] = now
        }

        val args = TodoDeleteArgs(id = id.value)
        assertThat(todoRepositoryImpl.delete(args)).isTrue()
    }

    @Test
    fun deleteWithNonExistId() {
        assertThat {
            val args = TodoDeleteArgs(id = ulid())
            todoRepositoryImpl.delete(args)
        }.isFailure().isInstanceOf(ULIDNotFoundException::class)
    }

    @Test
    fun findById() {
        val now = Instant.now()
        val id = Todos.insertAndGetId {
            it[title] = "test"
            it[completed] = false
            it[createdAt] = now
            it[updatedAt] = now
        }
        val todo = todoRepositoryImpl.findById(id.value)
        assertThat(todo.id).isEqualTo(id.value)
        assertThat(todo.title).isEqualTo("test")
        assertThat(todo.completed).isEqualTo(false)
        assertThat(todo.createdAt).isEqualTo(now)
        assertThat(todo.updatedAt).isEqualTo(now)
    }

    @Test
    fun findByIdWithNonExistId() {
        assertThat {
            todoRepositoryImpl.findById(ulid())
        }.isFailure().isInstanceOf(ULIDNotFoundException::class)
    }

    @Test
    fun findAll() {
        val now = Instant.now()
        val titles = listOf("foo", "bar", "buzz")
        Todos.batchInsert(titles) { title ->
            this[Todos.title] = title
            this[Todos.completed] = false
            this[Todos.createdAt] = now
            this[Todos.updatedAt] = now
        }
        val order = listOf(TodoOrderByArgs.Title(SortOrder.DESC))
        val todos = todoRepositoryImpl.findAll(TodoFindAllArgs(title = "b", orderBy = order))
        assertThat(todos).hasSize(2)
        assertThat(todos).extracting { it.title }.containsExactly("buzz", "bar")
    }
}