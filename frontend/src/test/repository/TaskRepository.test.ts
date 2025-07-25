import { describe, expect } from "vitest";
import axios from "axios";
import { TaskRepositoryImpl } from "@/app/repository/TaskRepository.ts";

describe("TaskRepository", () => {
  it("saveTask を呼んだとき、/api/tasks に task を POST する", async () => {
    // Given
    vi.spyOn(axios, "post").mockResolvedValue("");
    const taskRepository = new TaskRepositoryImpl();

    // When
    await taskRepository.saveTask({
      title: "タスクをバックエンドに送信するよ",
    });

    // Then
    expect(axios.post).toHaveBeenCalledWith("api/tasks", {
      title: "タスクをバックエンドに送信するよ",
    });
  });

  it("saveTask が失敗したとき、エラーをキャッチしてログに出力する", async () => {
    // Given
    const spyConsole = vi.spyOn(console, "log");
    vi.spyOn(axios, "post").mockRejectedValue(new Error("API エラー"));
    const taskRepository = new TaskRepositoryImpl();

    // When
    await taskRepository.saveTask({ title: "送信が失敗したとき" });

    // Then
    expect(spyConsole).toHaveBeenCalledWith(
      expect.objectContaining({ message: "API エラー" }),
    );
  });
});
