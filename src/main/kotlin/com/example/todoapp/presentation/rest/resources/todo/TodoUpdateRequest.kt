package com.example.todoapp.presentation.rest.resources.todo

import javax.validation.constraints.Size

data class TodoUpdateRequest(
    @get:Size(min = 1)
    val title: String? = null,
    val completed: Boolean? = null
)