import { beforeEach, describe, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { userEvent } from "@testing-library/user-event";
import { TaskRepository } from "@/repository/TaskRepository.ts";

describe("TodoPage", () => {
  const noOpTaskRepository = {} as TaskRepository;
  let user: ReturnType<typeof userEvent.setup>;
  let spyTaskRepository: TaskRepository;

  beforeEach(() => {
    user = userEvent.setup();
    spyTaskRepository = {
      saveTask: vi.fn(),
    };
  });

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
    it("テキスト入力欄にフォーカスがあたっていて Enter キーを押したとき、タスクは送信されないこと", async () => {
      // Given
      render(<TodoPage taskRepository={spyTaskRepository} />);

      // When
      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "テストタスク{enter}");

      // Then
      expect(spyTaskRepository.saveTask).not.toHaveBeenCalled();
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
    it("add ボタンにフォーカスがあるときに Enter キーでタスクが送信されること", async () => {
      // Given
      render(<TodoPage taskRepository={spyTaskRepository} />);

      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "テストタスク");
      await user.tab();

      // When
      await user.keyboard("{enter}");

      // Then
      expect(spyTaskRepository.saveTask).toHaveBeenCalledWith("テストタスク");
    });

    describe("addボタンをクリックしたとき", () => {
      it("入力したタスクが、バックエンドに送信する関数に渡されていること", async () => {
        // Given
        render(<TodoPage taskRepository={spyTaskRepository} />);

        const input = screen.getByRole("textbox", { name: "todo:" });
        await user.type(input, "テストタスク");

        // When
        await user.click(screen.getByRole("button", { name: "add" }));

        //Then
        expect(spyTaskRepository.saveTask).toHaveBeenCalledWith("テストタスク");
      });
      it("入力欄をクリアする", async () => {
        // Given
        render(<TodoPage taskRepository={spyTaskRepository} />);

        const input = screen.getByRole("textbox", { name: "todo:" });
        await user.type(input, "テストタスク");

        // When
        await user.click(screen.getByRole("button", { name: "add" }));

        // Then
        expect(input).toHaveValue("");
      });
      it("入力欄が空白のとき、バックエンドに送信する関数は呼ばれないこと", async () => {
        // Given
        render(<TodoPage taskRepository={spyTaskRepository} />);

        // When
        await user.click(screen.getByRole("button", { name: "add" }));

        // Then
        expect(spyTaskRepository.saveTask).not.toHaveBeenCalled();
      });
    });
  });
});
