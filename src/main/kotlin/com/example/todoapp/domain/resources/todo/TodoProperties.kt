package com.example.todoapp.domain.resources.todo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@ConstructorBinding
@Validated
@ConfigurationProperties("app.todo")
data class TodoProperties(
    @get:NotEmpty
    val value: String
)
