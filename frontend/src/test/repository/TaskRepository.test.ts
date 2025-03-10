import { describe, expect } from "vitest";
import { TaskRepositoryImpl } from "@/repository/TaskRepository.ts";
import axios from "axios";

describe("TaskRepository", () => {
  it("saveTask を呼んだとき、/api/task に task を POST する", async () => {
    // Given
    vi.spyOn(axios, "post").mockResolvedValue("");
    const taskRepository = new TaskRepositoryImpl();

    // When
    await taskRepository.saveTask({ task: "タスクをバックエンドに送信するよ" });

    // Then
    expect(axios.post).toHaveBeenCalledWith("api/task", {
      task: "タスクをバックエンドに送信するよ",
    });
  });

  it("aveTask が失敗したとき、エラーをキャッチしてログに出力する", async () => {
    // Given
    const spyConsole = vi.spyOn(console, "log");
    vi.spyOn(axios, "post").mockRejectedValue(new Error("API エラー"));
    const taskRepository = new TaskRepositoryImpl();

    // When
    await taskRepository.saveTask({ task: "送信が失敗したとき" });

    // Then
    expect(spyConsole).toHaveBeenCalledWith(
      expect.objectContaining({ message: "API エラー" }),
    );
  });
});
