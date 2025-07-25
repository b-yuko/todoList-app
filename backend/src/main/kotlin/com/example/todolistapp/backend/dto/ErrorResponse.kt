package com.example.todolistapp.backend.dto

data class ErrorResponse(
    val message: String,
    val status: Int,
    val error: String,
    val path: String,
    val timestamp: Long,
)
