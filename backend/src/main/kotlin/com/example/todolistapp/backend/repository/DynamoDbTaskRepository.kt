package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.model.TaskModel
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

interface TaskRepository {
    fun save(model: TaskModel): Result<Unit>
}

@Repository
class DynamoDbTaskRepository(
    private val table: DynamoDbTable<TaskModel>,
) : TaskRepository {
    override fun save(model: TaskModel): Result<Unit> =
        runCatching {
            table.putItem(model)
        }
}
