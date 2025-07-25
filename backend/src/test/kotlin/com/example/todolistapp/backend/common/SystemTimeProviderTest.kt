package com.example.todolistapp.backend.common

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class SystemTimeProviderTest {
    @Test
    fun `nowAsIso8601 が正常に値を返すこと`() {
        // Given
        val timeProvider = SystemTimeProvider()

        // When
        val result = timeProvider.nowAsIso8601()

        // Then
        assertNotNull(result, "現在時刻が生成されること")
    }
}
