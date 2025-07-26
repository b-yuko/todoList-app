package com.example.todolistapp.backend.repository.impl

import com.example.todolistapp.backend.domain.model.common.SavedTaskId
import com.example.todolistapp.backend.domain.model.task.Task
import com.example.todolistapp.backend.entity.TaskEntity
import com.example.todolistapp.backend.repository.TaskRepository
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

@Repository
class TaskRepositoryImpl(
    private val table: DynamoDbTable<TaskEntity>,
    private val logger: Logger,
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
