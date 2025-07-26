package com.example.todolistapp.service

import com.example.todolistapp.controller.dto.CreateTaskRequest
import com.example.todolistapp.controller.dto.TaskResponse

interface TaskService {
    fun createTask(request: CreateTaskRequest): Result<TaskResponse>
}
