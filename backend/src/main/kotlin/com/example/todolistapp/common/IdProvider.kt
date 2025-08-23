package com.example.todolistapp.common

import org.springframework.stereotype.Component
import java.util.UUID

interface IdProvider {
    fun generate(): String
}

@Component
class UuidProvider : IdProvider {
    override fun generate(): String = UUID.randomUUID().toString()
}
