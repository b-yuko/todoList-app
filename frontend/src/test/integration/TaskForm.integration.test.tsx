import { beforeEach, describe, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import { TaskForm } from "@/component/TaskForm.tsx";
import type { TaskRepository } from "@/repository/TaskRepository.ts";
import { userEvent } from "@testing-library/user-event";

describe("TaskForm Integration Tests", () => {
  let user: ReturnType<typeof userEvent.setup>;
  let spyTaskRepository: TaskRepository;

  beforeEach(() => {
    user = userEvent.setup();
    spyTaskRepository = {
      saveTask: vi.fn(),
    };
  });

  it("テキスト入力欄にフォーカスがあたっていて Enter キーを押したとき、タスクは送信されない", async () => {
    // Given

    render(<TaskForm taskRepository={spyTaskRepository} />);

    // When
    const input = screen.getByRole("textbox", { name: "todo:" });
    await user.type(input, "テストタスク{enter}");

    // Then
    expect(spyTaskRepository.saveTask).not.toHaveBeenCalled();
  });

  it("add ボタンにフォーカスがあるときに Enter キーでタスクが送信される", async () => {
    // Given
    render(<TaskForm taskRepository={spyTaskRepository} />);

    const input = screen.getByRole("textbox", { name: "todo:" });
    await user.type(input, "テストタスク");
    await user.tab();

    // When
    await user.keyboard("{enter}");

    // Then
    expect(spyTaskRepository.saveTask).toHaveBeenCalledWith("テストタスク");
  });

  describe("todo タスクを追加するボタン をクリックしたとき", () => {
    it("入力したタスクを、バックエンドに送信する関数に渡す", async () => {
      // Given
      render(<TaskForm taskRepository={spyTaskRepository} />);

      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "テストタスク");

      // When
      await user.click(screen.getByRole("button", { name: "add" }));

      //Then
      expect(spyTaskRepository.saveTask).toHaveBeenCalledWith("テストタスク");
    });

    it("入力欄をクリアする", async () => {
      // Given
      render(<TaskForm taskRepository={spyTaskRepository} />);

      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "テストタスク");

      // When
      await user.click(screen.getByRole("button", { name: "add" }));

      // Then
      expect(input).toHaveValue("");
    });

    it("入力欄が空のとき、バックエンドに送信する関数を呼び出さない", async () => {
      // Given
      render(<TaskForm taskRepository={spyTaskRepository} />);

      // When
      await user.click(screen.getByRole("button", { name: "add" }));

      // Then
      expect(spyTaskRepository.saveTask).not.toHaveBeenCalled();
    });
  });
});
