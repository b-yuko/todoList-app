package com.example.todolistapp.backend

import org.springframework.context.annotation.Configuration

@Configuration
class DynamoDbProperties {
    val dynamoDbUrl: String = "http://localhost:8000"
    val dynamoDbRegion: String = "ap-northeast-1"
    val dynamoDbTableName: String = "todo-tasks-local"
}
