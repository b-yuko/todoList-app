package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.common.IdProvider
import com.example.todolistapp.backend.common.TimeProvider
import com.example.todolistapp.backend.model.TaskModel
import com.example.todolistapp.backend.repository.TaskRepository
import org.springframework.stereotype.Service

interface TaskService {
    fun saveTask(task: String)
}

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val idProvider: IdProvider,
    private val timeProvider: TimeProvider,
) : TaskService {
    override fun saveTask(task: String) {
        val taskId = idProvider.generate()
        val createdAt = timeProvider.nowEpochMilli()

        taskRepository.save(
            TaskModel(
                taskId = taskId,
                createdAt = createdAt,
                taskTitle = task,
            ),
        )
    }
}
