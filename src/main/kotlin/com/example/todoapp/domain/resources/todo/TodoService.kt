package com.example.todoapp.domain.resources.todo

import com.example.todoapp.domain.resources.base.ULID
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

private val log = KotlinLogging.logger { }

@Service
@Validated
class TodoService(
    private val todoRepository: TodoRepository,
    private val todoProperties: TodoProperties
) {
    @Transactional
    fun create(@Valid args: TodoCreateArgs): Todo {
        log.debug { todoProperties.value }
        return todoRepository.create(args)
    }

    @Transactional
    fun update(@Valid args: TodoUpdateArgs): Todo {
        log.debug { todoProperties.value }
        return todoRepository.update(args)
    }

    @Transactional
    fun delete(@Valid args: TodoDeleteArgs): Boolean {
        log.debug { todoProperties.value }
        return todoRepository.delete(args)
    }

    @Transactional(readOnly = true)
    fun getById(id: ULID): Todo {
        log.debug { todoProperties.value }
        return todoRepository.findById(id)
    }

    @Transactional(readOnly = true)
    fun getAll(@Valid args: TodoFindAllArgs): List<Todo> {
        log.debug { todoProperties.value }
        return todoRepository.findAll(args)
    }
}