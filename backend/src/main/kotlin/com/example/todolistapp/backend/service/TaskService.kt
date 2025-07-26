package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.dto.CreateTaskRequest
import com.example.todolistapp.backend.dto.TaskResponse

interface TaskService {
    fun createTask(request: CreateTaskRequest): Result<TaskResponse>
}
