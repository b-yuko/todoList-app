package com.example.todoList.controller

import com.example.todoList.service.TaskService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TaskControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var taskController: TaskController
    private lateinit var mockTaskService: TaskService

    @BeforeEach
    fun setUp() {
        mockTaskService = mockk()
        taskController = TaskController(mockTaskService)
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build()
    }

    @Test
    fun `api task に POST したとき、200 OK が返る`() {
        // Given
        every { mockTaskService.saveTask(any()) } returns Unit

        // When
        val result =
            mockMvc.perform(
                post("/api/task")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                            "task": "テストタスク"
                        }
                        """.trimIndent(),
                    ),
            )

        // Then
        result.andExpect(status().isOk)
    }

    @Test
    fun `api task に POST したとき、タスクをサービスに渡している`() {
        // Given
        every { mockTaskService.saveTask(any()) } returns Unit

        // When
        mockMvc.perform(
            post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "task": "テストタスク"
                    }
                    """.trimIndent(),
                ),
        )

        // Then
        verify { mockTaskService.saveTask("テストタスク") }
    }
}
