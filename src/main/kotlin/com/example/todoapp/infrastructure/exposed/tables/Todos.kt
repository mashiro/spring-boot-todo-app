package com.example.todoapp.infrastructure.exposed.tables

import com.example.todoapp.infrastructure.exposed.dao.id.ULIDTable
import org.jetbrains.exposed.sql.`java-time`.timestamp

object Todos : ULIDTable("todos") {
    val title = varchar("title", 255)
    val completed = bool("completed").index()
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
}