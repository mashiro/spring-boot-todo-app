package com.example.todoapp.presentation.rest.extensions

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.mock.web.MockHttpServletResponse

inline fun <reified T : Any> MockHttpServletResponse.contentAsJsonObject(objectMapper: ObjectMapper): T {
    return objectMapper.readValue(this.contentAsString, object : TypeReference<T>() {})
}
