import { Route, Routes } from "react-router";
import { TodoPage } from "@/app/component/TodoPage.tsx";
import { TaskRepositoryImpl } from "@/app/repository/TaskRepository.ts";

export function App() {
  const taskRepository = new TaskRepositoryImpl();

  return (
    <Routes>
      <Route path="/" element={<TodoPage taskRepository={taskRepository} />} />
    </Routes>
  );
}
