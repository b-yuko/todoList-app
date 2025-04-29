package com.example.todolistapp.backend.model

data class KeyValueModel(
    // パーティションキー
    var taskId: String = "",
    // ソートキー(UTC 数値で保存する)
    var createdAt: Long = 0L,
    // タスクのタイトル (NonNull)
    var taskTitle: String = "",
)
