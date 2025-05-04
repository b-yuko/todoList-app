package com.example.todolistapp.backend.service

import com.example.todolistapp.backend.common.IdProvider
import com.example.todolistapp.backend.common.TimeProvider
import com.example.todolistapp.backend.model.KeyValueModel
import com.example.todolistapp.backend.repository.KeyValueRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class TaskServiceImplTest {
    @Test
    fun `saveTask が呼ばれたとき、Repository に保存を要求する`() {
        // Given
        val spyKeyValueRepository = mockk<KeyValueRepository>(relaxed = true)
        val stubIdProvider = mockk<IdProvider>()
        val stubTimeProvider = mockk<TimeProvider>()

        val expectedTaskId = "testTaskId"
        val expectedCreatedAt = 1_000_000L

        every { stubIdProvider.generate() } returns expectedTaskId
        every { stubTimeProvider.nowEpochMilli() } returns expectedCreatedAt

        val service = TaskServiceImpl(spyKeyValueRepository, stubIdProvider, stubTimeProvider)

        // When
        service.saveTask("テスト用のタスクです")

        // Then
        verify {
            spyKeyValueRepository.save(
                KeyValueModel(
                    taskId = expectedTaskId,
                    createdAt = expectedCreatedAt,
                    taskTitle = "テスト用のタスクです",
                ),
            )
        }
    }
}
