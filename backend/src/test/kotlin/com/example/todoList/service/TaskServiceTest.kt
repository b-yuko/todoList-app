package com.example.todoList.service

import com.example.todoList.model.KeyValueModel
import com.example.todoList.repository.KeyValueRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.UUID

class TaskServiceTest {
    @Test
    fun `saveTask が呼ばれたとき、Repository に保存を要求する`() {
        // GIVEN
        val spyKeyValueRepository = mockk<KeyValueRepository>()
        val service = TaskServiceImpl(spyKeyValueRepository)

        val stubDateNow = ZonedDateTime.parse("2024-10-15T20:10:10.123Z")
        mockkStatic(ZonedDateTime::class)
        every { ZonedDateTime.now() } returns stubDateNow

        every { spyKeyValueRepository.save(any()) } returns Unit

        val expectedId = UUID.fromString("9aed437f-b91d-4fe2-bbb3-33a5db3cfdce")
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns expectedId

        // WHEN
        service.saveTask(TaskSaveItem(taskName = "天気予報を確認する"))

        // THEN
        verify {
            spyKeyValueRepository.save(
                KeyValueModel(
                    pk = "@LIST@9aed437f-b91d-4fe2-bbb3-33a5db3cfdce",
                    sk = "2024-10-15T20:10:10.123Z",
                    taskName = "天気予報を確認する",
                ),
            )
        }
    }
}
