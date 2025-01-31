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
      expect(screen.getByRole("textbox")).toBeInTheDocument();
    });
  });
});
