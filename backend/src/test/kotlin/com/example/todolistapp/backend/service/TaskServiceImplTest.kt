package com.example.todolistapp.backend.service

import com.example.todolistapp.common.IdProvider
import com.example.todolistapp.common.TimeProvider
import com.example.todolistapp.controller.dto.TaskRequest
import com.example.todolistapp.controller.dto.TaskResponse
import com.example.todolistapp.domain.Task
import com.example.todolistapp.repository.TaskRepository
import com.example.todolistapp.service.TaskServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TaskServiceImplTest {
    private lateinit var spyTaskRepository: TaskRepository
    private lateinit var stubIdProvider: IdProvider
    private lateinit var stubTimeProvider: TimeProvider

    @BeforeEach
    fun setUp() {
        spyTaskRepository = mockk<TaskRepository>()
        stubIdProvider = mockk<IdProvider>()
        stubTimeProvider = mockk<TimeProvider>()
    }

    @Nested
    @DisplayName("正常系")
    inner class SuccessScenarios {
        @Test
        fun `createTask が成功したとき、正しい TaskResponse を返す`() {
            // Given
            val expectedTaskId = "testTaskId"
            val expectedCreatedAt = "2024-01-15T10:30:45.123Z"
            val expectedTitle = "テスト用のタスクです１"

            val expectedTask = Task(id = expectedTaskId, createdAt = expectedCreatedAt, title = expectedTitle)
            val expectedResponse = TaskResponse(id = expectedTaskId, createdAt = expectedCreatedAt)

            every { stubIdProvider.generate() } returns expectedTaskId
            every { stubTimeProvider.nowAsIso8601() } returns expectedCreatedAt
            every { spyTaskRepository.save(expectedTask) } returns Result.success(expectedTask)

            val taskService = TaskServiceImpl(spyTaskRepository, stubIdProvider, stubTimeProvider)

            // When
            val request = TaskRequest(title = expectedTitle)
            val result = taskService.createTask(request)

            // Then
            assert(result.isSuccess)
            assert(result.getOrThrow() == expectedResponse)
        }

        @Test
        fun `createTask 正常系で Repository save が正しい Task 引数で呼び出される`() {
            // Given
            val expectedTaskId = "testTaskId"
            val expectedCreatedAt = "2025-05-23T05:45:00.000Z"
            val expectedTitle = "テスト用のタスクです２"

            val expectedTask = Task(id = expectedTaskId, createdAt = expectedCreatedAt, title = expectedTitle)

            every { stubIdProvider.generate() } returns expectedTaskId
            every { stubTimeProvider.nowAsIso8601() } returns expectedCreatedAt
            every { spyTaskRepository.save(expectedTask) } returns Result.success(expectedTask)

            val taskService = TaskServiceImpl(spyTaskRepository, stubIdProvider, stubTimeProvider)

            // When
            val request = TaskRequest(title = expectedTitle)
            taskService.createTask(request)

            // Then
            verify { spyTaskRepository.save(expectedTask) }
        }
    }

    @Nested
    @DisplayName("異常系")
    inner class ErrorScenarios {
        @Test
        fun `Repository保存が失敗したとき、失敗結果を返す`() {
            // Given
            val expectedTaskId = "testTaskId"
            val expectedCreatedAt = "2024-01-15T10:30:45.123Z"
            val expectedError = RuntimeException("保存に失敗しました")

            every { stubIdProvider.generate() } returns expectedTaskId
            every { stubTimeProvider.nowAsIso8601() } returns expectedCreatedAt
            every { spyTaskRepository.save(any()) } returns Result.failure(expectedError)

            val taskService = TaskServiceImpl(spyTaskRepository, stubIdProvider, stubTimeProvider)

            // When
            val request = TaskRequest(title = "テスト用のタスクです")
            val result = taskService.createTask(request)

            // Then
            assert(result.isFailure)
            assert(result.exceptionOrNull() == expectedError)
        }
    }
}
