package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.model.KeyValueModel
import org.springframework.stereotype.Repository

@Repository
class DynamoDBRepository : KeyValueRepository {
    override fun save(model: KeyValueModel): KeyValueModel {
        TODO("Not yet implemented")
    }
}
