package com.example.todoapp.domain.exceptions

open class DomainException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message ?: cause?.toString(), cause)