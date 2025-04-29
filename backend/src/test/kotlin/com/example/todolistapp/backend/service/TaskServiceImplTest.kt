package com.example.todolistapp.backend.service

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
        val service = TaskServiceImpl(spyKeyValueRepository)

        val expectedTaskId = "testTaskId"
        mockkStatic(UUID::class)
        every { UUID.randomUUID().toString() } returns expectedTaskId

        val fixedInstant = Instant.parse("2025-04-29T09:13:37.470Z")
        val expectedCreatedAt = fixedInstant.toEpochMilli()
        mockkStatic(Instant::class)
        every { Instant.now() } returns fixedInstant

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

        unmockkStatic(Instant::class)
        unmockkStatic(UUID::class)
    }
}
