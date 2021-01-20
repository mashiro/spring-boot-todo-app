package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ULID
import javax.validation.constraints.Size

data class TodoUpdateArgs(
    val id: ULID,
    @get:Size(min = 1, max = 200)
    val title: String? = null,
    val completed: Boolean? = null
)