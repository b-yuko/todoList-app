package com.example.todolistapp.controller.dto

import jakarta.validation.constraints.NotBlank

data class TaskRequest(
    @field:NotBlank(message = "タイトルを入力してください")
    val title: String,
)
