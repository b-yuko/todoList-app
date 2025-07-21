package com.example.todolistapp.backend.entity

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class TaskEntity(
    // パーティションキー
    @get:DynamoDbPartitionKey
    var id: String = "",
    // ソートキー(UTC 数値で保存する)
    @get:DynamoDbSortKey
    var createdAt: Long = 0L,
    // タスクのタイトル (NonNull)
    var title: String = "",
)
