package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.common.IdProvider
import com.example.todolistapp.backend.common.TimeProvider
import com.example.todolistapp.backend.domain.model.task.Task
import com.example.todolistapp.backend.dto.CreateTaskRequest
import com.example.todolistapp.backend.dto.TaskResponse
import com.example.todolistapp.backend.repository.TaskRepository
import org.springframework.stereotype.Service

interface TaskService {
    fun createTask(createTaskRequest: CreateTaskRequest): Result<TaskResponse>
}

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val idProvider: IdProvider,
    private val timeProvider: TimeProvider,
) : TaskService {
    override fun createTask(createTaskRequest: CreateTaskRequest): Result<TaskResponse> {
        val task = buildTask(createTaskRequest)

        return taskRepository.save(task).map { savedTask ->
            TaskResponse(
                id = savedTask.id,
                createdAt = savedTask.createdAt,
            )
        }
    }

    private fun buildTask(createTaskRequest: CreateTaskRequest): Task {
        val taskId = idProvider.generate()
        val createdAt = timeProvider.nowAsIso8601()

        return Task(
            id = taskId,
            createdAt = createdAt,
            title = createTaskRequest.title,
        )
    }
}
