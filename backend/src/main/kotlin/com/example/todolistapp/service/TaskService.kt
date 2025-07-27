package com.example.todolistapp.service

import com.example.todolistapp.controller.dto.TaskRequest
import com.example.todolistapp.controller.dto.TaskResponse

interface TaskService {
    fun createTask(request: TaskRequest): Result<TaskResponse>
}
