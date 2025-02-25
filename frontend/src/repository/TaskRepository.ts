import axios from "axios";

export interface TaskRepository {
  saveTask: (task: string) => Promise<void>;
}

export class TaskRepositoryImpl implements TaskRepository {
  async saveTask(task: string): Promise<void> {
    try {
      const response = await axios.post("api/task", task);

      // TODO: 最終的に削除するコード
      // --- 開発中のみ ↓ ---
      if (response.status === 200) {
        console.log("200 OK: taskRepository.saveTask(task)");
      }
      // --- 開発中のみ ↑ ---
    } catch (error) {
      console.log(error);
    }
  }
}
