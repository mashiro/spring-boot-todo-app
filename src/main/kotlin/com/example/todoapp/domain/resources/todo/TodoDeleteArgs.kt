package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ULID

data class TodoDeleteArgs(
    val id: ULID
)