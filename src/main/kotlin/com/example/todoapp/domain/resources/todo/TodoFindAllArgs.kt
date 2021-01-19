package com.example.todoapp.domain.resources.todo

data class TodoFindAllArgs(
    val title: String? = null,
    val completed: Boolean? = null,
    val orderBy: List<TodoOrderByArgs>? = null
)