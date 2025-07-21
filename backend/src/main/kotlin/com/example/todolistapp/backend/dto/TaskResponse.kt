package com.example.todolistapp.backend.dto

data class TaskResponse(
    val id: String,
    val createdAt: Long,
    val title: String,
)
