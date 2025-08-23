package com.example.todolistapp.repository

import com.example.todolistapp.domain.Task
import com.example.todolistapp.repository.entity.TaskEntity
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

@Repository
class TaskRepositoryImpl(
    private val table: DynamoDbTable<TaskEntity>,
    private val logger: Logger,
) : TaskRepository {
    override fun save(task: Task): Result<Task> =
        runCatching {
            logger.info("Saving task: ID={}, title={}", task.id, task.title)

            val taskEntity =
                TaskEntity(
                    id = task.id,
                    createdAt = task.createdAt,
                    title = task.title,
                )

            table.putItem(taskEntity)

            Task(
                id = taskEntity.id,
                createdAt = taskEntity.createdAt,
                title = taskEntity.title,
            )
        }.onSuccess {
            logger.info("Successfully saved task: ID={}, title={}", task.id, task.title)
        }.onFailure {
            logger.error("Failed to save task with ID: ${task.id}, title: ${task.title}", it)
        }
}
