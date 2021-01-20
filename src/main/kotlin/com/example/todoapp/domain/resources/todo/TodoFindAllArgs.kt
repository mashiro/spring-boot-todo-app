package com.example.todoapp.domain.resources.todo

import javax.validation.constraints.Size

data class TodoFindAllArgs(
    @get:Size(min = 1, max = 200)
    val title: String? = null,
    val completed: Boolean? = null,
    val orderBy: List<TodoOrderByArgs>? = null
)