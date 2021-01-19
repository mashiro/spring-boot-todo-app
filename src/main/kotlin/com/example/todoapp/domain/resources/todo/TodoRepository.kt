package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.Repository
import com.example.todoapp.domain.resources.base.ULID

interface TodoRepository : Repository {
    fun create(args: TodoCreateArgs): Todo
    fun update(args: TodoUpdateArgs): Todo
    fun delete(args: TodoDeleteArgs): Boolean
    fun findById(id: ULID): Todo
    fun findAll(args: TodoFindAllArgs): List<Todo>
}