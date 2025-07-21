package com.example.todolistapp.backend.dto

data class ErrorResponse(
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    val error: String,
    val path: String,
)
