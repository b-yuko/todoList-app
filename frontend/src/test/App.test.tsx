import { describe, expect } from "vitest";
import { App } from "@/App.tsx";
import { MemoryRouter } from "react-router";
import { render } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";

vi.mock("@/component/TodoPage");

describe("App", () => {
  it("/ をレンダーしたとき、TodoPage が呼ばれていること", () => {
    // Given

    // When
    render(
      <MemoryRouter initialEntries={["/"]}>
        <App />
      </MemoryRouter>,
    );

    // Then
    expect(TodoPage).toHaveBeenCalled();
  });
});
