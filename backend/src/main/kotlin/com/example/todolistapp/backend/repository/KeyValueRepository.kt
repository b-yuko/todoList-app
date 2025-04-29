package com.example.todolistapp.backend.repository

import com.example.todolistapp.backend.model.KeyValueModel

interface KeyValueRepository {
    fun save(model: KeyValueModel): KeyValueModel
}
