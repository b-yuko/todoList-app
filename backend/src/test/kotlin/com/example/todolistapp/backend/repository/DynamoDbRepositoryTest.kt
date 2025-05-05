package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.model.TaskModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

class DynamoDbRepositoryTest {
    private fun createTestTaskModel(): TaskModel =
        TaskModel(
            taskId = "test-id",
            createdAt = 1234567890000,
            taskTitle = "Test Task",
        )

    @Test
    fun `save すると DynamoDB に putItem する`() {
        // Given
        val spyTable = mockk<DynamoDbTable<TaskModel>>(relaxed = true)
        val repository = DynamoDbTaskRepository(spyTable)
        val inputModel = createTestTaskModel()

        // When
        repository.save(inputModel)

        // Then
        verify { spyTable.putItem(inputModel) }
    }

    @Test
    fun `save が成功した場合 Result_success を返す`() {
        // Given
        val table = mockk<DynamoDbTable<TaskModel>>(relaxed = true)
        val repository = DynamoDbTaskRepository(table)
        val inputModel = createTestTaskModel()

        // When
        val result = repository.save(inputModel)

        // Then
        assertTrue(result.isSuccess)
        verify { table.putItem(inputModel) }
    }

    @Test
    fun `save が失敗した場合 Result_failure を返す`() {
        // Given
        val table = mockk<DynamoDbTable<TaskModel>>()
        val repository = DynamoDbTaskRepository(table)
        val inputModel = createTestTaskModel()
        val exception = RuntimeException("DynamoDB error")

        every { table.putItem(any<TaskModel>()) } throws exception

        // When
        val result = repository.save(inputModel)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify { table.putItem(inputModel) }
    }
}
