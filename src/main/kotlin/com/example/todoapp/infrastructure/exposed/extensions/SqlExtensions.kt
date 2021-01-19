package com.example.todoapp.infrastructure.exposed.extensions

import com.example.todoapp.domain.resources.base.SortOrder
import org.jetbrains.exposed.sql.SortOrder as ExposedSortOrder

fun SortOrder.toExposed(): ExposedSortOrder {
    return when (this) {
        SortOrder.ASC -> ExposedSortOrder.ASC
        SortOrder.DESC -> ExposedSortOrder.DESC
    }
}