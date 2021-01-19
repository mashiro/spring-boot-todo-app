package com.example.todoapp.infrastructure.testcontainers

import org.junit.jupiter.api.extension.*

class MySQLContainerExtension : BeforeAllCallback, AfterAllCallback, ParameterResolver {
    companion object {
        private const val CONTAINER_KEY = "container"
        private const val IMAGE_NAME = "mysql:5.7"
        private val NAMESPACE = ExtensionContext.Namespace.create(MySQLContainerExtension::class.java)!!
    }

    override fun beforeAll(context: ExtensionContext) {
        val container = KMySQLContainer(IMAGE_NAME)
        container.start()

        getStore(context).put(CONTAINER_KEY, container)
    }

    override fun afterAll(context: ExtensionContext) {
        val container = getContainer(context)
        container.stop()
    }

    private fun getContainer(context: ExtensionContext): KMySQLContainer {
        return getStore(context).get(CONTAINER_KEY, KMySQLContainer::class.java)
    }

    private fun getStore(context: ExtensionContext): ExtensionContext.Store {
        return context.getStore(NAMESPACE)
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == KMySQLContainer::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return getContainer(extensionContext)
    }
}