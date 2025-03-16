package com.example.todoList.service

import com.example.todoList.model.KeyValueModel
import com.example.todoList.repository.KeyValueRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class TaskSaveItem(
    val taskName: String,
)

interface TaskService {
    fun saveTask(taskName: TaskSaveItem)
}

@Service
class TaskServiceImpl(
    val keyValueRepository: KeyValueRepository,
) : TaskService {
    override fun saveTask(taskName: TaskSaveItem) {
        val formatIsoNow = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        val taskId = UUID.randomUUID().toString()

        keyValueRepository.save(
            KeyValueModel(
                pk = "@LIST@$taskId",
                sk = formatIsoNow,
                taskName = taskName.taskName,
            ),
        )
    }
}
