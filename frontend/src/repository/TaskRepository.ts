import axios from "axios";

export interface TaskRepository {
  saveTask: (task: string) => Promise<void>;
}

export class TaskRepositoryImpl implements TaskRepository {
  async saveTask(task: string): Promise<void> {
    try {
      await axios.post("api/task", task);
    } catch (error) {
      console.log(error);
    }
  }
}
