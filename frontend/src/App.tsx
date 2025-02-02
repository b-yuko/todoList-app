import { Route, Routes } from "react-router";
import { TodoPage } from "@/component/TodoPage.tsx";
import { TaskRepositoryImpl } from "@/repository/TaskRepositoryImpl.ts";

export function App() {
  const taskRepository = new TaskRepositoryImpl();

  return (
    <Routes>
      <Route path="/" element={<TodoPage taskRepository={taskRepository} />} />
    </Routes>
  );
}
