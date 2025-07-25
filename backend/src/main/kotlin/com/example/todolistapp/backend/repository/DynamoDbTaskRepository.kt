package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.domain.model.common.SavedTaskId
import com.example.todolistapp.backend.domain.model.task.Task
import com.example.todolistapp.backend.entity.TaskEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

@Repository
class DynamoDbTaskRepository(
    private val table: DynamoDbTable<TaskEntity>,
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    private val logger: Logger = LoggerFactory.getLogger(DynamoDbTaskRepository::class.java),
) : TaskRepository {
    override fun save(task: Task): Result<SavedTaskId> =
        runCatching {
            logger.info("Saving task: ID={}, title={}", task.id, task.title)

            val taskEntity =
                TaskEntity(
                    id = task.id,
                    createdAt = task.createdAt,
                    title = task.title,
                )

            table.putItem(taskEntity)

            SavedTaskId(
                id = taskEntity.id,
                createdAt = taskEntity.createdAt,
            )
        }.onSuccess {
            logger.info("Successfully saved task: ID={}, title={}", task.id, task.title)
        }.onFailure {
            logger.error("Failed to save task with ID: ${task.id}, title: ${task.title}", it)
        }
}
