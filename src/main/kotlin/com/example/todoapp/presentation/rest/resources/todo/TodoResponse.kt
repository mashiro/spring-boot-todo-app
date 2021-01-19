package com.example.todoapp.presentation.rest.resources.todo

import com.example.todoapp.domain.resources.base.ULID
import java.time.OffsetDateTime

data class TodoResponse(
    val id: ULID,
    val title: String,
    val completed: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)