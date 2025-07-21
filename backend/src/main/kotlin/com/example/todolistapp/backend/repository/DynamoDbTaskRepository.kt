package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.entity.TaskEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

interface TaskRepository {
    fun save(model: TaskEntity): Result<Unit>
}

@Repository
class DynamoDbTaskRepository(
    private val table: DynamoDbTable<TaskEntity>,
) : TaskRepository {
    private val logger = LoggerFactory.getLogger(DynamoDbTaskRepository::class.java)

    override fun save(model: TaskEntity): Result<Unit> =
        runCatching {
            logger.debug("TaskModel class loader: {}", TaskEntity::class.java.classLoader)
            logger.debug("model instance class loader: {}", model.javaClass.classLoader)

            table.putItem(model)
        }.onFailure {
            logger.error("Failed to save TaskModel", it)
        }
}
