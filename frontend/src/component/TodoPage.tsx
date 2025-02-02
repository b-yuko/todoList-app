import { TaskRepository } from "@/repository/TaskRepository.ts";
import { FormEvent, useState } from "react";

type TopPageProps = {
  taskRepository: TaskRepository;
};

export function TodoPage({ taskRepository }: TopPageProps) {
  const [taskInput, setTaskInput] = useState("");

  const handleAddTask = async (e: FormEvent) => {
    e.preventDefault();

    await taskRepository.saveTask(taskInput);
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
        />
      </label>
      <button type="submit">add</button>
    </form>
  );
}
