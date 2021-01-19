package com.example.todoapp.presentation.rest.resources.todo

import assertk.assertThat
import assertk.assertions.hasSameSizeAs
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.example.todoapp.domain.resources.base.ulid
import com.example.todoapp.domain.resources.todo.*
import com.example.todoapp.presentation.rest.extensions.contentAsJsonObject
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(TodoController::class)
internal class TodoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var todoService: TodoService

    private fun buildTodo(title: String): Todo {
        val now = Instant.now()
        return Todo(
            id = ulid(),
            title = title,
            completed = false,
            createdAt = now,
            updatedAt = now
        )
    }

    @Test
    fun create() {
        val todo = buildTodo("test")
        val args = TodoCreateArgs(todo.title)
        every { todoService.create(args) } returns todo

        val actual = mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(args))
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsJsonObject<TodoResponse>(objectMapper)

        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt.toInstant()).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt.toInstant()).isEqualTo(todo.updatedAt)

        verify { todoService.create(args) }
    }

    @Test
    fun update() {
        val todo = buildTodo("test")
        val args = TodoUpdateArgs(id = todo.id, completed = true)
        every { todoService.update(args) } returns todo

        val actual = mockMvc.perform(
            put("/todos/{id}", todo.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(args))
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsJsonObject<TodoResponse>(objectMapper)

        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt.toInstant()).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt.toInstant()).isEqualTo(todo.updatedAt)

        verify { todoService.update(args) }
    }

    @Test
    fun delete() {
        val todo = buildTodo("test")
        val args = TodoDeleteArgs(id = todo.id)
        every { todoService.delete(args) } returns true

        val actual = mockMvc.perform(delete("/todos/{id}", todo.id))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsJsonObject<Boolean>(objectMapper)

        assertThat(actual).isTrue()

        verify { todoService.delete(args) }
    }

    @Test
    fun getById() {
        val todo = buildTodo("test")
        every { todoService.getById(todo.id) } returns todo

        val actual = mockMvc.perform(get("/todos/{id}", todo.id))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsJsonObject<TodoResponse>(objectMapper)

        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt.toInstant()).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt.toInstant()).isEqualTo(todo.updatedAt)

        verify { todoService.getById(todo.id) }
    }

    @Test
    fun getAll() {
        val todos = listOf(
            buildTodo("foo"),
            buildTodo("bar"),
            buildTodo("buzz")
        )
        val args = TodoFindAllArgs(title = "b", completed = true)
        every { todoService.getAll(args) } returns todos

        val response = mockMvc.perform(
            get("/todos")
                .param("title", "b")
                .param("completed", "1")
        )
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsJsonObject<List<TodoResponse>>(objectMapper)

        assertThat(response).hasSameSizeAs(todos)
        response.zip(todos).forEach { (actual, expected) ->
            assertThat(actual.id).isEqualTo(expected.id)
            assertThat(actual.title).isEqualTo(expected.title)
            assertThat(actual.completed).isEqualTo(expected.completed)
            assertThat(actual.createdAt.toInstant()).isEqualTo(expected.createdAt)
            assertThat(actual.updatedAt.toInstant()).isEqualTo(expected.updatedAt)
        }

        verify { todoService.getAll(args) }
    }
}