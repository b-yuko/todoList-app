import axios from "axios";

export type Task = {
  title: string;
};

export interface TaskRepository {
  saveTask: (task: Task) => Promise<void>;
}

export class TaskRepositoryImpl implements TaskRepository {
  async saveTask(task: Task): Promise<void> {
    try {
      await axios.post("api/tasks", task);
    } catch (error) {
      console.log(error);
    }
  }
}
