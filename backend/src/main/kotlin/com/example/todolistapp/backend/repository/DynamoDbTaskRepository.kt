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
            println("TaskModel class loader: ${TaskModel::class.java.classLoader}")
            println("model instance class loader: ${model.javaClass.classLoader}")

            table.putItem(model)
        }.onFailure {
            it.printStackTrace() // ここで例外内容を標準出力に出す
        }
}
