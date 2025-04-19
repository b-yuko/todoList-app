package com.example.todolistapp.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController {
    @PostMapping("/api/task")
    fun createTask() {}
}
