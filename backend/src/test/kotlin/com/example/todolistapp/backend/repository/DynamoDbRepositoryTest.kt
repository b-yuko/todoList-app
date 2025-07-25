package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.domain.model.task.Task
import com.example.todolistapp.backend.entity.TaskEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException

class DynamoDbRepositoryTest {
    private lateinit var table: DynamoDbTable<TaskEntity>
    private lateinit var logger: Logger
    private lateinit var repository: DynamoDbTaskRepository

    companion object {
        private const val FIXED_TIME = "2024-01-15T10:30:45.123Z"
    }

    private val testTask =
        Task(
            id = "test-id",
            createdAt = FIXED_TIME,
            title = "Test Task",
        )

    private val testTaskEntity =
        TaskEntity(
            id = "test-id",
            createdAt = FIXED_TIME,
            title = "Test Task",
        )

    @BeforeEach
    fun setUp() {
        table = mockk()
        logger = mockk(relaxed = true)
        repository = DynamoDbTaskRepository(table, logger)
    }

    @Test
    fun `save() でタスクが正常に保存されること`() {
        // Given
        every { table.putItem(any<TaskEntity>()) } returns Unit

        // When
        val result = repository.save(testTask)

        // Then
        assertTrue(result.isSuccess)
        verify { table.putItem(testTaskEntity) }
    }

    @Test
    fun `DynamoDB操作で例外が発生した場合、失敗として扱うこと`() {
        // Given
        every { table.putItem(any<TaskEntity>()) } throws
            DynamoDbException.builder().message("Connection failed").build()

        // When
        val result = repository.save(testTask)

        // Then
        assertTrue(result.isFailure)
        verify { table.putItem(testTaskEntity) }
    }

    @Test
    fun `DynamoDB操作で例外が発生した場合、エラーログが出力されること`() {
        // Given
        repository = DynamoDbTaskRepository(table, logger)

        every { table.putItem(any<TaskEntity>()) } throws
            DynamoDbException.builder().message("Connection failed").build()

        // When
        repository.save(testTask)

        // Then
        verify {
            logger.error(
                match { it.contains(testTask.id) },
                any<DynamoDbException>(),
            )
        }
    }
}
