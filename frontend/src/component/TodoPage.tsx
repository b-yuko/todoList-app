export function TodoPage() {
  return (
    <form>
      <label>
        todo:
        <input name="todoTask" placeholder="タスクを入力" />
      </label>
      <button type="submit">add</button>
    </form>
  );
}
