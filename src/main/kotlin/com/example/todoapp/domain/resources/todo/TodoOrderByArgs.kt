package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.SortOrder

sealed class TodoOrderByArgs {
    data class Id(val order: SortOrder) : TodoOrderByArgs()
    data class Title(val order: SortOrder) : TodoOrderByArgs()
}