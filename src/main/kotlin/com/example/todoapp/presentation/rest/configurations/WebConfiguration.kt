package com.example.todoapp.presentation.rest.configurations

import com.example.todoapp.presentation.rest.converters.StringToULIDConverter
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToULIDConverter())
    }
}

@Configuration
@Profile("local")
class LocalWebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}
