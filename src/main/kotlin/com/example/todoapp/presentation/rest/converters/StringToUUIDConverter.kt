package com.example.todoapp.presentation.rest.converters

import org.springframework.core.convert.converter.Converter
import java.util.*

class StringToUUIDConverter : Converter<String, UUID> {
    override fun convert(source: String): UUID? {
        return UUID.fromString(source)
    }
}