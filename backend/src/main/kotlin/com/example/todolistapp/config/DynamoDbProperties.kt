package com.example.todolistapp.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "dynamo.datasource")
class DynamoDbProperties {
    @NotBlank
    lateinit var url: String

    @NotBlank
    lateinit var region: String

    @NotBlank
    lateinit var tableName: String
}
