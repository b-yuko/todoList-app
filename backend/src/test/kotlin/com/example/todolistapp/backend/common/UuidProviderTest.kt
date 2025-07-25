package com.example.todolistapp.backend.common

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class UuidProviderTest {
    @Test
    fun `generate が正常に値を返すこと`() {
        // Given
        val idProvider = UuidProvider()

        // When
        val result = idProvider.generate()

        // Then
        assertNotNull(result, "IDが生成されること")
    }
}
