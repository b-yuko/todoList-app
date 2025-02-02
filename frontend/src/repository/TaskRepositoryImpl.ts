export interface TaskRepository {
  saveTask: (task: string) => Promise<void>;
}

export class TaskRepositoryImpl implements TaskRepository {
  async saveTask(task: string): Promise<void> {
    console.log(task);
  }
}
