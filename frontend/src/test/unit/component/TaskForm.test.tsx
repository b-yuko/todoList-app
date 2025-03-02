import { beforeEach, describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import { TaskForm } from "@/component/TaskForm.tsx";
import type { TaskRepository } from "@/repository/TaskRepository.ts";
import { userEvent } from "@testing-library/user-event";

describe("TaskForm Unit Tests", () => {
  const noOpTaskRepository = {} as TaskRepository;
  let user: ReturnType<typeof userEvent.setup>;

  beforeEach(() => {
    user = userEvent.setup();
  });

  describe("todo タスクを入できる", () => {
    it("todo タスクを入力する テキスト入力欄があり プレースホルダーが設定されている", () => {
      // Given

      // When
      render(<TaskForm taskRepository={noOpTaskRepository} />);

      // Then
      expect(
        screen.getByRole("textbox", { name: "todo:" }),
      ).toBeInTheDocument();
      expect(screen.getByPlaceholderText("タスクを入力")).toBeInTheDocument();
    });

    it("文字が入力されているテキスト入力欄に フォーカスがあたっていて Enter キーを押したとき、入力されている文字が消えない", async () => {
      // Given
      render(<TaskForm taskRepository={noOpTaskRepository} />);

      // When
      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "Enter押しても消えないで！{enter}");

      // Then
      expect(input).toHaveValue("Enter押しても消えないで！");
    });
  });

  it("todoタスクを送信するボタンがある", () => {
    // Given

    // When
    render(<TaskForm taskRepository={noOpTaskRepository} />);

    // Then
    const button = screen.getByRole("button", { name: "add" });
    expect(button).toBeInTheDocument();
  });
});
