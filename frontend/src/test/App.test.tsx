import { describe, expect } from "vitest";
import { App } from "@/app/App.tsx";
import { MemoryRouter } from "react-router";
import { render } from "@testing-library/react";
import { TodoPage } from "@/app/component/TodoPage.tsx";
import { TaskRepositoryImpl } from "@/app/repository/TaskRepository.ts";

vi.mock("@/app/component/TodoPage", () => ({
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
      expect.anything();
    });
  });
});
