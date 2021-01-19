package com.example.todoapp.infrastructure.testcontainers

import org.testcontainers.containers.MySQLContainer

class KMySQLContainer(dockerImageName: String) : MySQLContainer<KMySQLContainer>(dockerImageName)