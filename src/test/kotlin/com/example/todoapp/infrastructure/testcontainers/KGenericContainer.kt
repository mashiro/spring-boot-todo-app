package com.example.todoapp.infrastructure.testcontainers

import org.testcontainers.containers.GenericContainer

class KGenericContainer(dockerImageName: String) : GenericContainer<KGenericContainer>(dockerImageName)