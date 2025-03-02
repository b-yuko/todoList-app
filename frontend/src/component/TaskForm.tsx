import { KeyboardEvent } from "react";
import { useTaskForm } from "@/hooks/useTaskForm.ts";
import { TaskRepository } from "@/repository/TaskRepository.ts";

type TaskFormProps = {
  taskRepository: TaskRepository;
};

export function TaskForm({ taskRepository }: TaskFormProps) {
  const { taskInput, setTaskInput, handleAddTask } =
    useTaskForm(taskRepository);

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
    }
  };

  return (
    <form onSubmit={handleAddTask} aria-label="inputTaskForm">
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
