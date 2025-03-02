import { FormEvent, useState } from "react";
import { TaskRepository } from "@/repository/TaskRepository.ts";

export function useTaskForm(taskRepository: TaskRepository) {
  const [taskInput, setTaskInput] = useState("");

  const handleAddTask = async (e: FormEvent) => {
    e.preventDefault();
    if (taskInput === "") return;

    await taskRepository.saveTask(taskInput);
    setTaskInput("");
  };

  return {
    taskInput,
    setTaskInput,
    handleAddTask,
  };
}
