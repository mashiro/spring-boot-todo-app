package com.example.todoapp.presentation.rest.configurations

import com.example.todoapp.presentation.rest.converters.StringToUUIDConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToUUIDConverter())
    }
}