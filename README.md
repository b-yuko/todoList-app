# todoList-app

todoList-appは、フロントエンドからバックエンドまで一貫して開発できるようにするための練習プロジェクトです。  
**現在作成途中です。**

## 概要

- フロントエンド: React + TypeScript + Vite
- バックエンド: Kotlin + Spring Boot
- データベース: DynamoDB Local（Docker利用）
- Docker Composeによるローカル開発環境構築

## ディレクトリ構成

```
todoList-app/
├── backend/          # Spring Boot (Kotlin) バックエンド
├── docker/           # DynamoDB Local用データ
├── frontend/         # React (Vite, TypeScript) フロントエンド
├── compose.yml       # Docker Compose 設定
├── Makefile          # 開発用コマンド
└── README.md         # このファイル
```

## セットアップ

### 前提

- Node.js, npm
- Java 17 以上
- Docker, Docker Compose

### 1. DynamoDB Local の起動

```sh
docker compose up
```

### 2. バックエンドの起動

プロジェクトルートで以下のコマンドを実行してください。  

```sh
make back
```

### 3. フロントエンドの起動

初回のみ依存パッケージをインストールしてください。  

```sh
cd frontend
npm install
```

プロジェクトルートで以下のコマンドを実行してください。  

```shell
make front
```

## テスト・チェック

### バックエンド

```sh
make back-check
```

### フロントエンド

```sh
make front-check
```
