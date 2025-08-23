package com.example.todolistapp.backend.controller

import com.example.todolistapp.controller.dto.TaskRequest
import com.example.todolistapp.repository.entity.TaskEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest
    @Autowired
    constructor(
        val mockMvc: MockMvc,
        val table: DynamoDbTable<TaskEntity>,
    ) {
        @BeforeEach
        fun setUp() {
            // テスト開始前にもテーブルをクリーンにする
            table.scan().items().forEach { table.deleteItem(it) }
        }

        @AfterEach
        fun tearDown() {
            // テストごとにテーブルをクリーンにする
            table.scan().items().forEach { table.deleteItem(it) }
        }

        @Test
        fun `POST api-tasks でタスクが作成される`() {
            // GIVEN: 新規タスクリクエストを用意
            val request = TaskRequest(title = "テストタスク")
            val json = """{"title":"${request.title}"}"""

            // WHEN: タスク作成 API を呼び出す
            mockMvc
                .post("/api/tasks") {
                    contentType = MediaType.APPLICATION_JSON
                    content = json
                }.andExpect {
                    status { isCreated() }
                    jsonPath("$.id") { exists() }
                    jsonPath("$.createdAt") { exists() }
                }.andReturn()

            // THEN: DBに1件だけ保存されていることを検証
            val all = table.scan().items().toList()
            assertEquals(1, all.size)
            assertEquals("テストタスク", all[0].title)
        }

        @Test
        fun `POST api-tasks タイトルが空文字のとき400が返る`() {
            // GIVEN
            val json = """{"title":""}"""

            // WHEN
            val result =
                mockMvc.post("/api/tasks") {
                    contentType = MediaType.APPLICATION_JSON
                    content = json
                }

            // THEN
            result.andExpect {
                status { isBadRequest() }
            }
        }
    }
