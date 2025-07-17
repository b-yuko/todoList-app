package com.example.todolistapp.backend.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class TaskModel(
    // パーティションキー
    @get:DynamoDbPartitionKey
    var taskId: String = "",
    // ソートキー(UTC 数値で保存する)
    @get:DynamoDbSortKey
    var createdAt: Long = 0L,
    // タスクのタイトル (NonNull)
    var taskTitle: String = "",
)
