package com.example.todolistapp.common

import org.springframework.stereotype.Component
import java.time.Instant

interface TimeProvider {
    fun nowAsIso8601(): String
}

@Component
class SystemTimeProvider : TimeProvider {
    override fun nowAsIso8601(): String = Instant.now().toString()
}
