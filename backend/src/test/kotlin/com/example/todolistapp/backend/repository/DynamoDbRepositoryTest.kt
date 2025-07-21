package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.entity.TaskEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

class DynamoDbRepositoryTest {
    private fun createTestTaskModel(): TaskEntity =
        TaskEntity(
            id = "test-id",
            createdAt = 1234567890000,
            title = "Test Task",
        )

    @Test
    fun `save() を呼ぶと table putItem が一度だけ呼ばれること`() {
        // Given
        val spyTable = mockk<DynamoDbTable<TaskEntity>>()
        val repository = DynamoDbTaskRepository(spyTable)
        val inputModel = createTestTaskModel()
        every { spyTable.putItem(any<TaskEntity>()) } returns Unit

        // When
        repository.save(inputModel)

        // Then
        verify(exactly = 1) { spyTable.putItem(inputModel) }
    }

    @Test
    fun `正常に保存できたとき、Result success(Unit) を返すこと`() {
        // Given
        val table = mockk<DynamoDbTable<TaskEntity>>(relaxed = true)
        val repository = DynamoDbTaskRepository(table)
        val inputModel = createTestTaskModel()

        // When
        val result = repository.save(inputModel)

        // Then
        assertTrue(result.isSuccess)
        verify(exactly = 1) { table.putItem(inputModel) }
    }

    @Test
    fun `save が失敗したとき Result_failure を返す`() {
        // Given
        val table = mockk<DynamoDbTable<TaskEntity>>()
        val repository = DynamoDbTaskRepository(table)
        val inputModel = createTestTaskModel()
        val exception = RuntimeException("DynamoDB error")

        every { table.putItem(any<TaskEntity>()) } throws exception

        // When
        val result = repository.save(inputModel)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(exactly = 1) { table.putItem(inputModel) }
    }
}
