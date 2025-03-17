package com.example.todolist.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodolistApplication

fun main(args: Array<String>) {
	runApplication<TodolistApplication>(*args)
}
