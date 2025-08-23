package com.example.todolistapp.repository.entity

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class TaskEntity(
    // パーティションキー
    @get:DynamoDbPartitionKey
    var id: String = "",
    // ソートキー(UTC ISO-8601文字列で保存する)
    @get:DynamoDbSortKey
    var createdAt: String = "",
    // タスクのタイトル (NonNull)
    var title: String = "",
)
