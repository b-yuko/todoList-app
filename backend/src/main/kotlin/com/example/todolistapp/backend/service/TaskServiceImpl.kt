package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.model.KeyValueModel
import com.example.todolistapp.backend.repository.KeyValueRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID.randomUUID

interface TaskService {
    fun saveTask(task: String)
}

@Service
class TaskServiceImpl(
    val keyValueRepository: KeyValueRepository,
) : TaskService {
    override fun saveTask(task: String) {
        val taskId = randomUUID().toString()
        val createdAt = Instant.now().toEpochMilli()

        keyValueRepository.save(
            KeyValueModel(
                taskId = taskId,
                createdAt = createdAt,
                taskTitle = task,
            ),
        )
    }
}
