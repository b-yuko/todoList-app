package com.example.todolistapp.backend.controller

import com.example.todolistapp.backend.dto.CreateTaskRequest
import com.example.todolistapp.backend.dto.TaskResponse
import com.example.todolistapp.backend.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/task")
class TaskController(
    private val taskService: TaskService,
) {
    @PostMapping
    fun createTask(
        @Valid @RequestBody request: CreateTaskRequest,
    ): ResponseEntity<TaskResponse> {
        val response = taskService.createTask(request)
        return ResponseEntity
            .created(URI.create("/api/task/${response.id}"))
            .body(response)
    }
}
