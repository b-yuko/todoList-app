package com.example.todolistapp.backend.common

import java.time.Instant
import org.springframework.stereotype.Component

interface TimeProvider {
    fun nowEpochMilli(): Long
}

@Component
class SystemTimeProvider : TimeProvider {
    override fun nowEpochMilli(): Long = Instant.now().toEpochMilli()
}