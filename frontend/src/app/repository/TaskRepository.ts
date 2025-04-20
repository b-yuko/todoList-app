import axios from "axios";

export type Task = {
  task: string;
};

export interface TaskRepository {
  saveTask: (task: Task) => Promise<void>;
}

export class TaskRepositoryImpl implements TaskRepository {
  async saveTask(task: Task): Promise<void> {
    try {
      await axios.post("api/task", task);
    } catch (error) {
      console.log(error);
    }
  }
}
