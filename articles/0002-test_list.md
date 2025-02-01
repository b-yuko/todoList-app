# Test List

ユーザがtodoタスクを入力してボタンを押したとき、入力したタスクと過去に入力したタスクが表示される機能

- [ ] todoタスクを入力できる
  - [x] テキスト入力欄がある
  - [x] 入力欄にプレースホルダーが設定されている

- [ ] todoタスクを追加するボタン
  - [ ] ボタンの仕様
    - [x] 「add」というボタンがある
    - [ ] 入力欄の値が空だったとき ボタンは押せない
  - [ ] ボタンをクリックしたとき
    - [ ] テキスト入力欄の値を state に保存する
    - [ ] taskRepository.saveTask を実行するときに task を渡している
    - [ ] todoタスクが上から順に時系列で表示される
      - [ ] taskRepository.getTask() を実行する
      - [ ] taskRepository.getTask() の戻り値が空だったとき「タスクはありません」と表示する

- [ ] TaskRepository
  - [ ] saveTask(task) が実行されたとき
    - [ ] /api/task に axios の post メソッドをたたいている
    - [ ] post するときに task を渡している
  - [ ] getTask() が実行されたとき
    - [ ] /api/task に axios の get メソッドをたたいている
    - [ ] task と 追加された日時がセットで BE から返ってくる
    - [ ] 登録されたタスクがなかったら空の配列が返ってくる





