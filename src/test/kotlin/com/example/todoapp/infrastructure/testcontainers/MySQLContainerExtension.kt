package com.example.todoapp.infrastructure.testcontainers

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class MySQLContainerExtension : BeforeAllCallback, ParameterResolver {
    companion object {
        private const val IMAGE_NAME = "mysql:5.7"
        private const val CONTAINER_KEY = "container"
        private val NAMESPACE = ExtensionContext.Namespace.create(MySQLContainerExtension::class.java)!!
    }

    class ContainerResource(
        val container: KMySQLContainer
    ) : ExtensionContext.Store.CloseableResource {
        override fun close() {
            container.stop()
        }
    }

    override fun beforeAll(context: ExtensionContext) {
        val store = getStore(context)
        store.getOrComputeIfAbsent(CONTAINER_KEY) { _: String ->
            val container = KMySQLContainer(IMAGE_NAME).apply { start() }
            ContainerResource(container)
        }
    }

    private fun getStore(context: ExtensionContext): ExtensionContext.Store {
        return context.root.getStore(NAMESPACE)
    }

    private fun getContainer(context: ExtensionContext): KMySQLContainer {
        return getStore(context).get(CONTAINER_KEY, ContainerResource::class.java).container
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == KMySQLContainer::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return getContainer(extensionContext)
    }
}