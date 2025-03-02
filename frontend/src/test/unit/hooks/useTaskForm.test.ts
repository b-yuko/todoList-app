import { renderHook, act } from "@testing-library/react";
import { useTaskForm } from "@/hooks/useTaskForm.ts";
import { TaskRepository } from "@/repository/TaskRepository.ts";
import { vi } from "vitest";
import { FormEvent } from "react";

describe("useTaskForm", () => {
  let spyTaskRepository: TaskRepository;

  beforeEach(() => {
    spyTaskRepository = {
      saveTask: vi.fn(),
    };
  });

  it("taskInput の初期値は空である", () => {
    // Given

    // When
    const { result } = renderHook(() => useTaskForm(spyTaskRepository));

    // Then
    expect(result.current.taskInput).toBe("");
  });

  it("setTaskInput を呼び出すと taskInput が更新される", () => {
    // Given
    const { result } = renderHook(() => useTaskForm(spyTaskRepository));

    // When
    act(() => {
      result.current.setTaskInput("New Task");
    });

    // Then
    expect(result.current.taskInput).toBe("New Task");
  });

  it("タスク追加時に saveTask を呼び出し、taskInput をクリアする", async () => {
    // Given
    const { result } = renderHook(() => useTaskForm(spyTaskRepository));
    act(() => {
      result.current.setTaskInput("New Task");
    });

    // When
    await act(async () => {
      await result.current.handleAddTask({
        preventDefault: () => {},
      } as FormEvent);
    });

    // Then
    expect(spyTaskRepository.saveTask).toHaveBeenCalledWith("New Task");
    expect(result.current.taskInput).toBe("");
  });

  it("taskInput が空のとき saveTask を呼び出さない", async () => {
    // Given
    const { result } = renderHook(() => useTaskForm(spyTaskRepository));

    // When
    await act(async () => {
      await result.current.handleAddTask({
        preventDefault: () => {},
      } as FormEvent);
    });

    // Then
    expect(spyTaskRepository.saveTask).not.toHaveBeenCalled();
  });
});
