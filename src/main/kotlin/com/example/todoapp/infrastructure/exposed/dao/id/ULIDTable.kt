package com.example.todoapp.infrastructure.exposed.dao.id

import com.example.todoapp.domain.resources.base.ulid
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

open class ULIDTable(name: String = "", columnName: String = "id") : IdTable<String>(name) {
    override val id: Column<EntityID<String>> = varchar(columnName, 64)
        .clientDefault { ulid() }
        .entityId()

    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}
