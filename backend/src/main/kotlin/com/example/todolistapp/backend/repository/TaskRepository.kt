package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.domain.model.common.SavedTaskId
import com.example.todolistapp.backend.domain.model.task.Task

interface TaskRepository {
    fun save(task: Task): Result<SavedTaskId>
}
