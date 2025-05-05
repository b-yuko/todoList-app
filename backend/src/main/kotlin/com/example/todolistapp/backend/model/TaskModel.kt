package com.example.todolistapp.backend.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class TaskModel(
    // パーティションキー
    var taskId: String = "",
    // ソートキー(UTC 数値で保存する)
    var createdAt: Long = 0L,
    // タスクのタイトル (NonNull)
    var taskTitle: String = "",
)
