package com.example.todoapp.domain.resources.base

import de.huxhorn.sulky.ulid.ULID as ULIDGen

private val ulidgen = object : ThreadLocal<ULIDGen>() {
    override fun initialValue() = ULIDGen()
}

typealias ULID = String

fun ulid(): ULID = ulidgen.get().nextULID()

fun ULID.toULIDObject() = ULIDGen.parseULID(this)

fun t() {
    val id = ulid()
    id.toULIDObject()
}