package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.common.IdProvider
import com.example.todolistapp.backend.common.TimeProvider
import com.example.todolistapp.backend.dto.CreateTaskRequest
import com.example.todolistapp.backend.dto.TaskResponse
import com.example.todolistapp.backend.entity.TaskEntity
import com.example.todolistapp.backend.repository.TaskRepository
import org.springframework.stereotype.Service

interface TaskService {
    fun createTask(createTaskRequest: CreateTaskRequest): TaskResponse
}

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val idProvider: IdProvider,
    private val timeProvider: TimeProvider,
) : TaskService {
    override fun createTask(createTaskRequest: CreateTaskRequest): TaskResponse {
        val taskModel = buildTaskModel(createTaskRequest)
        taskRepository.save(taskModel)
        return TaskResponse(
            id = taskModel.id,
            createdAt = taskModel.createdAt,
            title = taskModel.title,
        )
    }

    private fun buildTaskModel(createTaskRequest: CreateTaskRequest): TaskEntity {
        val taskId = idProvider.generate()
        val createdAt = timeProvider.nowEpochMilli()
        return TaskEntity(
            id = taskId,
            createdAt = createdAt,
            title = createTaskRequest.title,
        )
    }
}
