package com.example.todoapp.domain.exceptions

import com.example.todoapp.domain.resources.base.ULID

class ULIDNotFoundException(
    override val id: ULID,
    message: String? = null,
    cause: Throwable? = null
) : NotFoundException(id, message, cause)