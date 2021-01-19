package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ulid
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
internal class TodoServiceTest {
    @MockkBean
    lateinit var todoProperties: TodoProperties

    @MockkBean
    lateinit var todoRepository: TodoRepository

    @Autowired
    lateinit var todoService: TodoService

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
        every { todoProperties.value } returns "from test create"

        val todo = buildTodo("test")
        val args = TodoCreateArgs(title = todo.title)
        every { todoRepository.create(args) } returns todo

        val actual = todoService.create(args)
        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt).isEqualTo(todo.updatedAt)

        verify { todoRepository.create(args) }
    }

    @Test
    fun update() {
        every { todoProperties.value } returns "from test update"

        val todo = buildTodo("test")
        val args = TodoUpdateArgs(id = todo.id)
        every { todoRepository.update(args) } returns todo

        val actual = todoService.update(args)
        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt).isEqualTo(todo.updatedAt)

        verify { todoRepository.update(args) }
    }

    @Test
    fun delete() {
        every { todoProperties.value } returns "from test delete"

        val id = ulid()
        val args = TodoDeleteArgs(id = id)
        every { todoRepository.delete(args) } returns true

        val actual = todoService.delete(args)
        assertThat(actual).isTrue()

        verify { todoRepository.delete(args) }
    }

    @Test
    fun getById() {
        every { todoProperties.value } returns "from test getById"

        val todo = buildTodo("test")
        every { todoRepository.findById(todo.id) } returns todo

        val actual = todoService.getById(todo.id)
        assertThat(actual.id).isEqualTo(todo.id)
        assertThat(actual.title).isEqualTo(todo.title)
        assertThat(actual.completed).isEqualTo(todo.completed)
        assertThat(actual.createdAt).isEqualTo(todo.createdAt)
        assertThat(actual.updatedAt).isEqualTo(todo.updatedAt)

        verify { todoRepository.findById(todo.id) }
    }

    @Test
    fun getAll() {
        every { todoProperties.value } returns "from test getAll"
        val todos = listOf(
            buildTodo("test"),
            buildTodo("bar"),
            buildTodo("buzz")
        )
        val args = TodoFindAllArgs(title = "b")
        every { todoRepository.findAll(args) } returns todos

        val actuals = todoService.getAll(args)
        assertThat(actuals).hasSameSizeAs(todos)
        actuals.zip(todos).forEach { (actual, expected) ->
            assertThat(actual.id).isEqualTo(expected.id)
            assertThat(actual.title).isEqualTo(expected.title)
            assertThat(actual.completed).isEqualTo(expected.completed)
            assertThat(actual.createdAt).isEqualTo(expected.createdAt)
            assertThat(actual.updatedAt).isEqualTo(expected.updatedAt)
        }

        verify { todoRepository.findAll(args) }
    }
}