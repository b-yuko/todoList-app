package com.example.todolistapp.backend.service

import org.springframework.stereotype.Service

interface TaskService {
    fun saveTask(task: String)
}

@Service
class TaskServiceImpl : TaskService {
    override fun saveTask(task: String) {
        println(task)
    }
}
