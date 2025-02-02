import { describe, expect } from "vitest";
import { App } from "@/App.tsx";
import { MemoryRouter } from "react-router";
import { render } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { TaskRepositoryImpl } from "@/repository/TaskRepositoryImpl.ts";

vi.mock("@/component/TodoPage");

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
      expect(TodoPage).toHaveBeenCalledExactlyOnceWith(
        { taskRepository: new TaskRepositoryImpl() },
        expect.anything(),
      );
    });
  });
});
