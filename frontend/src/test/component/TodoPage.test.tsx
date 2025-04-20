import { beforeEach, describe, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import { userEvent } from "@testing-library/user-event";
import { TodoPage } from "@/app/component/TodoPage.tsx";
import { TaskRepository } from "@/app/repository/TaskRepository.ts";

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
    it("文字が入力されているテキスト入力欄に フォーカスがあたっていて Enter キーを押したとき、入力されている文字が消えないこと", async () => {
      // Given
      render(<TodoPage taskRepository={noOpTaskRepository} />);

      // When
      const input = screen.getByRole("textbox", { name: "todo:" });
      await user.type(input, "Enter押しても消えないで！{enter}");

      // Then
      expect(input).toHaveValue("Enter押しても消えないで！");
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
      expect(spyTaskRepository.saveTask).toHaveBeenCalledWith({
        task: "テストタスク",
      });
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
        expect(spyTaskRepository.saveTask).toHaveBeenCalledWith({
          task: "テストタスク",
        });
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
      it("入力欄が空のとき、バックエンドに送信する関数は呼ばれないこと", async () => {
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
