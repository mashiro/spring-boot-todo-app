package com.example.todoapp.presentation.rest

import com.example.todoapp.domain.exceptions.NotFoundException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class ExceptionHandler {
    data class ErrorResponse(
        val status: Int,
        val message: String
    )

    private fun buildErrorResponseEntity(
        status: HttpStatus,
        message: String? = null
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse(status = status.value(), message = message ?: status.reasonPhrase))
    }

    @ExceptionHandler(
        value = [
            NotFoundException::class,
            NoHandlerFoundException::class
        ]
    )
    fun handleNotFound(ex: Exception) = buildErrorResponseEntity(
        HttpStatus.NOT_FOUND
    )

    @ExceptionHandler(
        value = [
            BindException::class,
            TypeMismatchException::class
        ]
    )
    fun handleBadRequest(ex: Exception) = buildErrorResponseEntity(
        HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotAllowed(ex: Exception) = buildErrorResponseEntity(
        HttpStatus.METHOD_NOT_ALLOWED
    )

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleNotAcceptable(ex: Exception) = buildErrorResponseEntity(
        HttpStatus.NOT_ACCEPTABLE
    )

    @ExceptionHandler(Throwable::class)
    fun handleInternalServerError(ex: Throwable) = buildErrorResponseEntity(
        HttpStatus.INTERNAL_SERVER_ERROR
    )
}