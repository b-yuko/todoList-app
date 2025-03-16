package com.example.todoList.repository

import com.example.todoList.model.KeyValueModel
import org.springframework.stereotype.Repository

@Repository
class DynamoDbRepository : KeyValueRepository {
    override fun save(model: KeyValueModel) {
        TODO("Not yet implemented")
    }
}
