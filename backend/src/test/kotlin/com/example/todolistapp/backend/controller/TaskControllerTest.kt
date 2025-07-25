package com.example.todolistapp.backend.controller

import com.example.todolistapp.backend.dto.TaskResponse
import com.example.todolistapp.backend.handler.GlobalExceptionHandler
import com.example.todolistapp.backend.service.TaskService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TaskControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var taskController: TaskController
    private lateinit var mockTaskService: TaskService

    companion object {
        private const val FIXED_TIME = "2024-01-15T10:30:45.123Z"
    }

    @BeforeEach
    fun setUp() {
        // 厳格なモックを使用し、必要なメソッドを明示的にスタブ化
        mockTaskService = mockk()
        taskController = TaskController(mockTaskService)
        mockMvc =
            MockMvcBuilders
                .standaloneSetup(taskController)
                .setControllerAdvice(GlobalExceptionHandler())
                .build()
    }

    private fun setupRelaxedMockService() {
        // 例外発生のテストなど、メソッドの実際の振る舞いが重要でないテストケースでのみ使用する
        mockTaskService = mockk<TaskService>(relaxed = true)
        taskController = TaskController(mockTaskService)
        mockMvc =
            MockMvcBuilders
                .standaloneSetup(taskController)
                .setControllerAdvice(GlobalExceptionHandler())
                .build()
    }

    @Nested
    @DisplayName("正常系")
    inner class SuccessScenarios {
        @Test
        fun `api tasks に POST したとき、201 Created が返る`() {
            // Given
            val expectedResponse =
                TaskResponse(
                    id = "test-id-123",
                    createdAt = FIXED_TIME,
                )

            every { mockTaskService.createTask(any()) } returns Result.success(expectedResponse)

            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":"これがないとテストが失敗するよ"}"""),
                )

            // Then
            result.andExpect(status().isCreated)
        }

        @Test
        fun `api tasks に POST したとき、正しいリクエストデータがサービスに渡される`() {
            // Given
            setupRelaxedMockService()
            val expectedTitle = "テストタスク"

            // When
            mockMvc.perform(
                post("/api/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""{"title":"$expectedTitle"}"""),
            )

            // Then
            verify { mockTaskService.createTask(match { it.title == expectedTitle }) }
        }

        @Test
        fun `正常な POST リクエストで、期待通りのレスポンスボディが返る`() {
            // Given
            val expectedResponse =
                TaskResponse(
                    id = "test-id-123",
                    createdAt = FIXED_TIME,
                )

            every { mockTaskService.createTask(any()) } returns Result.success(expectedResponse)

            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":"テストタスク"}"""),
                )

            // Then
            result
                .andExpect(jsonPath("$.id").value(expectedResponse.id))
                .andExpect(jsonPath("$.createdAt").value(expectedResponse.createdAt))
        }

        @Test
        fun `Location ヘッダーが正しく設定されている`() {
            // Given
            val expectedResponse =
                TaskResponse(
                    id = "test-id-123",
                    createdAt = FIXED_TIME,
                )
            every { mockTaskService.createTask(any()) } returns Result.success(expectedResponse)

            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":"テストタスク"}"""),
                )

            // Then
            result.andExpect(header().string("Location", "/api/tasks/test-id-123"))
        }
    }

    @Nested
    @DisplayName("異常系")
    inner class ErrorScenarios {
        @Test
        fun `title が空文字のとき、400 Bad Request が返る`() {
            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":""}"""),
                )

            // Then
            result.andExpect(status().isBadRequest)
        }

        @Test
        fun `title が空文字のとき、適切なエラーメッセージが返る`() {
            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":""}"""),
                )

            // Then
            result
                .andExpect(jsonPath("$.message").value("タイトルを入力してください"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.path").value("/api/tasks"))
        }

        @Test
        fun `title が null のとき、400 Bad Request が返る`() {
            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":null}"""),
                )

            // Then
            result.andExpect(status().isBadRequest)
        }

        @Test
        fun `リクエストボディがない場合、400 Bad Request が返る`() {
            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON),
                )

            // Then
            result.andExpect(status().isBadRequest)
        }

        @Test
        fun `不正なJSONの場合、400 Bad Request が返る`() {
            // Given
            @Suppress("JsonStandardCompliance")
            val invalidJson = """{"title":"テストタスク"""

            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson),
                )

            // Then
            result.andExpect(status().isBadRequest)
        }

        @Test
        fun `Content-Type が application json でない場合、415 Unsupported Media Type が返る`() {
            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("テストタスク"),
                )

            // Then
            result.andExpect(status().isUnsupportedMediaType)
        }

        @Test
        fun `サービス層で例外が発生した場合、500 Internal Server Error が返る`() {
            // Given
            setupRelaxedMockService()
            every { mockTaskService.createTask(any()) } throws RuntimeException("サービスエラー")

            // When
            val result =
                mockMvc.perform(
                    post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""{"title":"テストタスク"}"""),
                )

            // Then
            result.andExpect(status().isInternalServerError)
        }
    }
}
