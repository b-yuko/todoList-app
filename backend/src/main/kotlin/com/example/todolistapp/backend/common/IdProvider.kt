package com.example.todolistapp.backend.common

import java.util.UUID
import org.springframework.stereotype.Component

interface IdProvider {
    fun generate(): String
}

@Component
class UuidProvider : IdProvider {
    override fun generate(): String = UUID.randomUUID().toString()
}