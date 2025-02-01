import { describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";

describe("TodoPage", () => {
  describe("todoタスクを入力できる", () => {
    it("テキスト入力欄がある", () => {
      // Given

      // When
      render(<TodoPage />);

      // Then
      expect(
        screen.getByRole("textbox", { name: "todo:" }),
      ).toBeInTheDocument();
    });

    it("入力欄にプレースホルダーが設定されている", () => {
      // Given

      // When
      render(<TodoPage />);

      // Then
      expect(screen.getByPlaceholderText("タスクを入力")).toBeInTheDocument();
    });
  });
  describe("todoタスクを送信するボタン", () => {
    it("todoタスクを送信するボタンがある", () => {
      // Given

      // When
      render(<TodoPage />);

      // Then
      const button = screen.getByRole("button", { name: "add" });
      expect(button).toBeInTheDocument();
      expect(button).toHaveAttribute("type", "submit");
    });
  });
});
