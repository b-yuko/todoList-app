package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.common.IdProvider
import com.example.todolistapp.backend.common.TimeProvider
import com.example.todolistapp.backend.model.TaskModel
import com.example.todolistapp.backend.repository.TaskRepository
import org.springframework.stereotype.Service

data class TaskRequest(
    val task: String,
)

interface TaskService {
    fun saveTask(taskRequest: TaskRequest)
}

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val idProvider: IdProvider,
    private val timeProvider: TimeProvider,
) : TaskService {
    override fun saveTask(taskRequest: TaskRequest) {
        val taskId = idProvider.generate()
        val createdAt = timeProvider.nowEpochMilli()

        taskRepository.save(
            TaskModel(
                taskId = taskId,
                createdAt = createdAt,
                taskTitle = taskRequest.task,
            ),
        )
    }
}
