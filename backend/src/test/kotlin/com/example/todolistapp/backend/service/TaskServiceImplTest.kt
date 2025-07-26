package com.example.todolistapp.backend.service

import com.example.todolistapp.common.IdProvider
import com.example.todolistapp.common.TimeProvider
import com.example.todolistapp.controller.dto.CreateTaskRequest
import com.example.todolistapp.domain.Task
import com.example.todolistapp.repository.TaskRepository
import com.example.todolistapp.service.TaskServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class TaskServiceImplTest {
    @Test
    fun `createTask が呼ばれたとき、Repository に保存を要求する`() {
        // Given
        val spyKeyValueRepository = mockk<TaskRepository>()
        val stubIdProvider = mockk<IdProvider>()
        val stubTimeProvider = mockk<TimeProvider>()

        val expectedTaskId = "testTaskId"
        val expectedCreatedAt = "2024-01-15T10:30:45.123Z"

        every { stubIdProvider.generate() } returns expectedTaskId
        every { stubTimeProvider.nowAsIso8601() } returns expectedCreatedAt
        every { spyKeyValueRepository.save(any()) } returns
            Result.success(
                Task(
                    id = expectedTaskId,
                    createdAt = expectedCreatedAt,
                    title = "テスト用のタスクです",
                ),
            )

        val service = TaskServiceImpl(spyKeyValueRepository, stubIdProvider, stubTimeProvider)

        // When
        val request = CreateTaskRequest(title = "テスト用のタスクです")
        service.createTask(request)

        // Then
        verify {
            spyKeyValueRepository.save(
                Task(
                    id = expectedTaskId,
                    createdAt = expectedCreatedAt,
                    title = "テスト用のタスクです",
                ),
            )
        }
    }
}
