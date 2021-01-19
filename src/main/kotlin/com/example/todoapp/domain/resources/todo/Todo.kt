package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ULID
import java.time.Instant

data class Todo(
    val id: ULID,
    val title: String,
    val completed: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)
