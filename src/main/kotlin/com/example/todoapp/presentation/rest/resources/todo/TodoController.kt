package com.example.todoapp.presentation.rest.resources.todo

import com.example.todoapp.domain.resources.base.ULID
import com.example.todoapp.domain.resources.todo.*
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.time.ZoneId
import javax.validation.Valid

@RestController
@RequestMapping("todos")
class TodoController(
    private val todoService: TodoService
) {
    @PostMapping("")
    fun create(@Valid @RequestBody req: TodoCreateRequest): TodoResponse {
        val args = TodoCreateArgs(title = req.title)
        val todo = todoService.create(args)
        return toResponse(todo)
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: ULID,
        @Valid @RequestBody req: TodoUpdateRequest
    ): TodoResponse {
        val args = TodoUpdateArgs(id = id, title = req.title, completed = req.completed)
        val todo = todoService.update(args)
        return toResponse(todo)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ULID): Boolean {
        val args = TodoDeleteArgs(id = id)
        return todoService.delete(args)
    }

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id: ULID): TodoResponse {
        val todo = todoService.getById(id)
        return toResponse(todo)
    }

    @GetMapping("")
    fun getAll(@Valid req: TodoGetAllRequest): List<TodoResponse> {
        val args = TodoFindAllArgs(title = req.title, completed = req.completed)
        val todos = todoService.getAll(args)
        return todos.map(this::toResponse)
    }

    private fun toResponse(todo: Todo): TodoResponse {
        return TodoResponse(
            id = todo.id,
            title = todo.title,
            completed = todo.completed,
            createdAt = OffsetDateTime.ofInstant(todo.createdAt, ZoneId.systemDefault()),
            updatedAt = OffsetDateTime.ofInstant(todo.updatedAt, ZoneId.systemDefault())
        )
    }
}