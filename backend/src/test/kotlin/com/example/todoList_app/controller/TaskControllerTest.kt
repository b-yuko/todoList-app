package com.example.todoList_app.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TaskControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var taskController: TaskController

    @BeforeEach
    fun setUp() {
        taskController = TaskController()
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build()
    }

    @Test
    fun `api task に POST したとき、200 OK が返る`(){
        // Given

        // When
        val response = mockMvc.perform(post("/api/task"))

        // Then
        response.andExpect(status().isOk)
    }
}