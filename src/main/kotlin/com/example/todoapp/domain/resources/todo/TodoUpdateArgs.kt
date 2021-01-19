package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ULID

data class TodoUpdateArgs(
    val id: ULID,
    val title: String? = null,
    val completed: Boolean? = null
)