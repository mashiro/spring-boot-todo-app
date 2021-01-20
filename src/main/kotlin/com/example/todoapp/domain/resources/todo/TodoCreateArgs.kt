package com.example.todoapp.domain.resources.todo

import javax.validation.constraints.Size

data class TodoCreateArgs(
    @get:Size(min = 1, max = 200)
    val title: String
)