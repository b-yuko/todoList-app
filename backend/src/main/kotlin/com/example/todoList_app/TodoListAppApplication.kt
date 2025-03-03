package com.example.todoList_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoListAppApplication

fun main(args: Array<String>) {
	runApplication<TodoListAppApplication>(*args)
}
