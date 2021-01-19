package com.example.todoapp.domain.exceptions

class InfrastructureException(
    message: String? = null,
    cause: Throwable? = null
) : DomainException(message, cause)