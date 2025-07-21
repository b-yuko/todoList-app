package com.example.todolistapp.backend.dto

import jakarta.validation.constraints.NotBlank

data class CreateTaskRequest(
    @field:NotBlank(message = "title must not be blank")
    val title: String,
)
