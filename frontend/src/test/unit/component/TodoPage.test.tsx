import { describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import { TodoPage } from "@/component/TodoPage.tsx";
import { TaskRepository } from "@/repository/TaskRepository.ts";
import { TaskForm } from "@/component/TaskForm.tsx";

vi.mock("@/component/TaskForm.tsx", () => ({
  TaskForm: vi.fn(),
}));

describe("TodoPage", () => {
  const noOpTaskRepository = {} as TaskRepository;

  it("Todo List の文字が見える", () => {
    // Given

    // When
    render(<TodoPage taskRepository={noOpTaskRepository} />);

    // Then
    expect(screen.getByText("Todo List")).toBeInTheDocument();
  });

  it("TaskForm コンポーネントが呼ばれ taskRepository を渡している", () => {
    // Given

    // When
    render(<TodoPage taskRepository={noOpTaskRepository} />);

    // Then
    expect(TaskForm).toHaveBeenCalled();
    expect(TaskForm).toHaveBeenCalledWith(
      expect.objectContaining({ taskRepository: noOpTaskRepository }),
      expect.anything(),
    );
  });
});
