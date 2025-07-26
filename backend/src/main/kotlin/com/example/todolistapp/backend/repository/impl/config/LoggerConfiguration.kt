package com.example.todolistapp.backend.repository.impl.config

import com.example.todolistapp.backend.repository.impl.TaskRepositoryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerConfiguration {
    @Bean
    fun taskRepositoryLogger(): Logger = LoggerFactory.getLogger(TaskRepositoryImpl::class.java)
}
