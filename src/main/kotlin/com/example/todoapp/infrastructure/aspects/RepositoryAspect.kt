package com.example.todoapp.infrastructure.aspects

import com.example.todoapp.domain.exceptions.DomainException
import com.example.todoapp.domain.exceptions.InfrastructureException
import com.example.todoapp.domain.resources.base.Repository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RepositoryAspect {
    @Around("target(repository)")
    fun wrapInfrastructureException(pjp: ProceedingJoinPoint, repository: Repository): Any {
        try {
            return pjp.proceed()
        } catch (ex: Throwable) {
            when (ex) {
                is DomainException -> {
                    throw ex
                }
                else -> {
                    throw InfrastructureException(cause = ex)
                }
            }
        }
    }
}