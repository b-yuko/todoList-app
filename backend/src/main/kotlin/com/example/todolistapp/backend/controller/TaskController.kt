package com.example.todolistapp.backend.controller

import com.example.todolistapp.backend.service.TaskService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class TaskRequest(
    val task: String,
)

@RestController
@RequestMapping("/api/task")
class TaskController(
    private val taskService: TaskService,
) {
    @PostMapping
    fun saveTask(
        @RequestBody request: TaskRequest,
    ) {
        taskService.saveTask(request.task)
    }
}
