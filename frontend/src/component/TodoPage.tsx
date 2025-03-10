import { TaskRepository } from "@/repository/TaskRepository.ts";
import { FormEvent, KeyboardEvent, useState } from "react";

type TopPageProps = {
  taskRepository: TaskRepository;
};

export function TodoPage({ taskRepository }: TopPageProps) {
  const [taskInput, setTaskInput] = useState("");

  const handleAddTask = async (e: FormEvent) => {
    e.preventDefault();
    if (taskInput === "") return;

    const activeElement = document.activeElement as HTMLElement;

    if (activeElement?.tagName === "BUTTON") {
      await taskRepository.saveTask({ task: taskInput });
    }

    setTaskInput("");
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
    }
  };

  return (
    <form onSubmit={handleAddTask}>
      <label>
        todo:
        <input
          name="todoTask"
          placeholder="タスクを入力"
          value={taskInput}
          onChange={(e) => setTaskInput(e.target.value)}
          onKeyDown={handleKeyDown}
        />
      </label>
      <button type="submit">add</button>
    </form>
  );
}
