package com.example.todolistapp.repository

import com.example.todolistapp.domain.Task

interface TaskRepository {
    fun save(task: Task): Result<Task>
}
