package com.example.todoList.repository

import com.example.todoList.model.KeyValueModel

interface KeyValueRepository {
    fun save(model: KeyValueModel)
}
