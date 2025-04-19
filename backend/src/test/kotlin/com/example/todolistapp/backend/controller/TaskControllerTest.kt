package com.example.todolistapp.backend.controller

import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.test.Test

class TaskControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var taskController: TaskController

    @BeforeEach
    fun setup() {
        taskController = TaskController()
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build()
    }

    @Test
    fun `api task に POST したとき、200 OK が返る`() {
        // GIVEN

        // WHEN
        val result =
            mockMvc.perform(
                post("/api/task")
                    .contentType(MediaType.APPLICATION_JSON),
            )

        // THEN
        result.andExpect(status().isOk)
    }
}
