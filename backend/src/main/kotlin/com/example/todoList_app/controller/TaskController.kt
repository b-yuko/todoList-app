package com.example.todoList_app.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task")
class TaskController {
    @PostMapping
    fun postTask(){}
}