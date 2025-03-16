import { describe, expect } from "vitest";
import { App } from "@/App.tsx";
import { MemoryRouter } from "react-router";
import { render } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { TaskRepositoryImpl } from "@/repository/TaskRepository.ts";

vi.mock("@/component/TodoPage", () => ({
  TodoPage: vi.fn(),
}));

describe("App", () => {
  describe("/ をレンダーしたとき", () => {
    it("TodoPage が taskRepository を受け取っていること", () => {
      // Given

      // When
      render(
        <MemoryRouter initialEntries={["/"]}>
          <App />
        </MemoryRouter>,
      );

      // Then
      expect(TodoPage).toHaveBeenCalledTimes(1);
      expect.objectContaining({
        taskRepository: expect.any(TaskRepositoryImpl),
      });
    });
  });
});
