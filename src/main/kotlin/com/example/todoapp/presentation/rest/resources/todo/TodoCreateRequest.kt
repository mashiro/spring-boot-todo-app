package com.example.todoapp.presentation.rest.resources.todo

import javax.validation.constraints.Size

data class TodoCreateRequest(
    @get:Size(min = 1)
    val title: String
)