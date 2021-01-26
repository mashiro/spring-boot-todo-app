package com.example.todoapp.presentation.rest.converters

import com.example.todoapp.domain.resources.base.ULID
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToULIDConverter : Converter<String, ULID> {
    override fun convert(source: String): ULID {
        // ULID is String typealias
        return source
    }
}