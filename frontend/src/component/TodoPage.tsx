import { TaskRepository } from "@/repository/TaskRepository.ts";
import { TaskForm } from "@/component/TaskForm.tsx";

type TopPageProps = {
  taskRepository: TaskRepository;
};

export function TodoPage({ taskRepository }: TopPageProps) {
  return (
    <div>
      <h1>Todo List</h1>
      <TaskForm taskRepository={taskRepository} />
    </div>
  );
}
