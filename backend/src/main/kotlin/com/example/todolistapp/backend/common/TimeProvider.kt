package com.example.todolistapp.backend.common

import org.springframework.stereotype.Component
import java.time.Instant

interface TimeProvider {
    fun nowEpochMilli(): Long
}

@Component
class SystemTimeProvider : TimeProvider {
    override fun nowEpochMilli(): Long = Instant.now().toEpochMilli()
}
