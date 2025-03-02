import { describe } from "vitest";
import { render, screen } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { TaskRepository } from "@/repository/TaskRepository.ts";

describe("TodoPage Integration Tests", () => {
  it("TaskForm が レンダリングされる", () => {
    // Given
    const noOpTaskRepository = {} as TaskRepository;

    // When
    render(<TodoPage taskRepository={noOpTaskRepository} />);

    // Then
    expect(
      screen.getByRole("form", { name: "inputTaskForm" }),
    ).toBeInTheDocument();
  });
});
