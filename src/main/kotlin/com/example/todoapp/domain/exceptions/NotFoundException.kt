package com.example.todoapp.domain.exceptions

abstract class NotFoundException(
    open val id: Any,
    message: String? = null,
    cause: Throwable? = null
) : DomainException(message, cause)
