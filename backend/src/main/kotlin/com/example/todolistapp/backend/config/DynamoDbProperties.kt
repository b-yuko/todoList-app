package com.example.todolistapp.backend.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "dynamo.datasource")
class DynamoDbProperties {
    @NotBlank
    lateinit var url: String

    @NotBlank
    lateinit var region: String

    @NotBlank
    lateinit var tableName: String
}