package com.example.todolistapp.backend.repository

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

    private val testTask = TaskEntity(
        id = "test-id",
        createdAt = 1234567890000,
        title = "Test Task"
    )

    @BeforeEach
    fun setUp() {
        table = mockk()
        logger = mockk(relaxed = true)
    }

    @Test
    fun `save() でタスクが正常に保存されること`() {
        // Given
        repository = DynamoDbTaskRepository(table)

        every { table.putItem(testTask) } returns Unit

        // When
        val result = repository.save(testTask)

        // Then
        assertTrue(result.isSuccess)
        verify { table.putItem(testTask) }
    }

    @Test
    fun `DynamoDB操作で例外が発生した場合、失敗として扱うこと`() {
        // Given
        repository = DynamoDbTaskRepository(table)

        every { table.putItem(any<TaskEntity>()) } throws
                DynamoDbException.builder().message("Connection failed").build()

        // When
        val result = repository.save(testTask)

        // Then
        assertTrue(result.isFailure)
        verify { table.putItem(testTask) }
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
                any<DynamoDbException>()
            )
        }
    }
}
