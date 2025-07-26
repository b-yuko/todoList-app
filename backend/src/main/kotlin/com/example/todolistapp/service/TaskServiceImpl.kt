package com.example.todolistapp.service

import com.example.todolistapp.common.IdProvider
import com.example.todolistapp.common.TimeProvider
import com.example.todolistapp.controller.dto.CreateTaskRequest
import com.example.todolistapp.controller.dto.TaskResponse
import com.example.todolistapp.domain.Task
import com.example.todolistapp.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val idProvider: IdProvider,
    private val timeProvider: TimeProvider,
) : TaskService {
    override fun createTask(request: CreateTaskRequest): Result<TaskResponse> {
        val task = buildTask(request)

        return taskRepository.save(task).map { savedTask ->
            TaskResponse(
                id = savedTask.id,
                createdAt = savedTask.createdAt,
            )
        }
    }

    private fun buildTask(request: CreateTaskRequest): Task {
        val taskId = idProvider.generate()
        val createdAt = timeProvider.nowAsIso8601()

        return Task(
            id = taskId,
            createdAt = createdAt,
            title = request.title,
        )
    }
}
