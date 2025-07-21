package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.entity.TaskEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

interface TaskRepository {
    fun save(task: TaskEntity): Result<Unit>
}

@Repository
class DynamoDbTaskRepository(
    private val table: DynamoDbTable<TaskEntity>,
    private val logger: Logger = LoggerFactory.getLogger(DynamoDbTaskRepository::class.java)
) : TaskRepository {

    override fun save(task: TaskEntity): Result<Unit> =
        runCatching {
            table.putItem(task)
        }.onFailure {
            logger.error("Failed to save task with ID: ${task.id}, title: ${task.title}", it)
        }
}
