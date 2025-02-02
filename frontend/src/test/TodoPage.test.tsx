import { describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { userEvent } from "@testing-library/user-event";
import { TaskRepository } from "@/repository/TaskRepository.ts";

describe("TodoPage", () => {
  const noOpTaskRepository = {} as TaskRepository;

  describe("todoタスクを入力できる", () => {
    it("テキスト入力欄がある", () => {
      // Given

      // When
      render(<TodoPage taskRepository={noOpTaskRepository} />);

      // Then
      expect(
        screen.getByRole("textbox", { name: "todo:" }),
      ).toBeInTheDocument();
    });

    it("入力欄にプレースホルダーが設定されている", () => {
      // Given

      // When
      render(<TodoPage taskRepository={noOpTaskRepository} />);

      // Then
      expect(screen.getByPlaceholderText("タスクを入力")).toBeInTheDocument();
    });
  });

  describe("todoタスクを送信するボタン", () => {
    it("todoタスクを送信するボタンがある", () => {
      // Given

      // When
      render(<TodoPage taskRepository={noOpTaskRepository} />);

      // Then
      const button = screen.getByRole("button", { name: "add" });
      expect(button).toBeInTheDocument();
    });

    describe("ボタンをクリックしたとき", () => {
      it("入力されたタスクをバックエンドに送信する関数を呼んでいること", async () => {
        // Given
        const user = userEvent.setup();

        const spyTaskRepository: TaskRepository = {
          saveTask: vi.fn(),
        };

        render(<TodoPage taskRepository={spyTaskRepository} />);

        // When
        await user.click(screen.getByRole("button", { name: "add" }));

        //Then
        expect(spyTaskRepository.saveTask).toHaveBeenCalledOnce();
      });
    });
  });
});
