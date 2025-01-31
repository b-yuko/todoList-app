import { Route, Routes } from "react-router";
import { TodoPage } from "@/component/TodoPage.tsx";

export function App() {
  return (
    <Routes>
      <Route path="/" element={<TodoPage />} />
    </Routes>
  );
}
