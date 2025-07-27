package com.example.todolistapp.controller

import com.example.todolistapp.controller.dto.TaskRequest
import com.example.todolistapp.controller.dto.TaskResponse
import com.example.todolistapp.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService,
) {
    @PostMapping
    fun createTask(
        @Valid @RequestBody request: TaskRequest,
    ): ResponseEntity<TaskResponse> {
        val result = taskService.createTask(request)

        return when {
            result.isSuccess -> {
                val response = result.getOrNull()!!
                ResponseEntity
                    .created(URI.create("/api/tasks/${response.id}"))
                    .body(response)
            }
            else -> {
                throw RuntimeException("タスクの作成に失敗しました", result.exceptionOrNull())
            }
        }
    }
}
