package com.example.todoapp.domain.configurations

import com.example.todoapp.domain.resources.todo.TodoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(TodoProperties::class)
class TodoConfiguration