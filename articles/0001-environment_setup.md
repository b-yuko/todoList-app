# 環境構築

# Vite プロジェクトを生成する
```shell
npm create vite@latest
```
- TypeScript, React, SWC  

```shell
cd frontend
npm install
```

# node のバージョンをプロジェクトに固定する
```shell
cd frontend
volta pin node@22.13
volta pin npm@10.9
```

# ESLint Config Inspector を導入する
ESLint Config Inspector は、ESLint の設定ファイルを解析して、プロジェクト内での ESLint の設定がどのように影響を与えるかを視覚的に理解するためのツールです。主に、ESLint の設定がどのルールにどのように適用されるかを調べるために使います。

このツールは、設定ファイル（例えば .eslintrc.json や .eslintrc.js）に書かれたルールや設定が、どのファイルやコードブロックにどう影響しているのかを可視化するのに役立ちます。特に、複雑な設定や複数の設定ファイルがある場合に、設定の優先順位や競合を簡単に把握することができます。

**frontend/package.json**  
```json
{
  "scripts": {
    "lint-config": "eslint --inspect-config"
  }
}
```

# "eslint-plugin-react" の導入と設定
eslint-plugin-react は、React プロジェクト向けの ESLint プラグインで、React 固有のコード品質を保つためのルールを提供します。React コンポーネントや JSX のコードに関して、より良いスタイルやベストプラクティスを強制することが目的です。
```shell
cd frontend
npm install eslint-plugin-react --save-dev
```
**frontend/eslint.config.js**
```js
import react from 'eslint-plugin-react'

export default tseslint.config(
    {
      extends: [
        react.configs.flat.recommended,   // React 用の推奨設定
        react.configs.flat['jsx-runtime'],   // React 17 以降の新しいJSX変換（jsx-runtime）向けの設定
      ],
      settings: { react: { version: 'detect' } },   // package.json から React のバージョンを自動的に識別
    },
)
```

# "eslint-plugin-jsx-a11y" の導入と設定
eslint-plugin-jsx-a11y は、React アプリケーションにおける アクセシビリティ（Accessibility） の問題を検出し、修正を促すための ESLint プラグインです。アクセシビリティとは、Web サイトやアプリがすべてのユーザーにとって使いやすくなるように設計されていることを指します。特に、視覚障害や運動障害などを持つユーザーがスムーズに利用できるようにすることが重要です。
```shell
cd frontend
npm install eslint-plugin-jsx-a11y --save-dev
```
**frontend/eslint.config.js**
```js
import jsxA11y from 'eslint-plugin-jsx-a11y'

export default tseslint.config(
    {
      extends: [jsxA11y.flatConfigs.recommended]
    }
)
```

# lint 対象のファイルを必要最小限にする
**frontend/eslint.config.js**
```js
export default tseslint.config(
    {
      ignores: [
        'dist',
        'src/**/*.d.{ts,tsx}'
      ]
    },
    {
      files: ['src/**/*.{ts,tsx}']
    }
)
```

# ESLint が検出した修正可能な問題を自動的に修正するオプションを追加する
**`--fix`**  
このオプションはESLintにできるだけ多くの問題を修正するように指示します。修正は実際のファイル自体に行われ、残りの未修正の問題のみが出力されます。全ての問題がこのオプションで修正できるわけではありません。

**frontend/package.json**  
```json
{
  "scripts": {
    "lint": "eslint . --fix"
  }
}
```

# Prettier の導入と設定
Prettierは、コードフォーマッター（コードを自動で整形するツール）です。特定のプログラミング言語やスタイルガイドに依存せず、一貫したコードスタイルを保つために使われます。

```shell
cd frontend
npm install --save-dev --save-exact prettier
```

**frontend/package.json**
```json
{
  "scripts": {
    "format": "prettier src/ --write"
  }
}
```

# Vitest の導入と設定
Vitest は、Viteに最適化された次世代のテストフレームワークです。主に、Viteを使用しているプロジェクトのために設計されていますが、Viteを使用しないプロジェクトでも利用可能です。Vitestは、Jestと互換性のあるAPIを提供しており、Jestの代わりに簡単に導入することができます。

```shell
cd frontend
npm install -D vitest
```

**frontend/package.json**
```json
{
  "scripts": {
    "test": "vitest run",
    "coverage": "vitest run --coverage"
  }
}
```

### jsdom の導入と設定
Node.js 環境で Web API をシミュレートするライブラリです。主に、ブラウザ環境で動作する JavaScript をサーバーサイド（Node.js）でも実行できるようにするために使われます。これにより、ブラウザ上でのみ動作する DOM 操作やイベント処理を、Node.js 上でテストやサーバーサイドレンダリング（SSR）用に模倣できます。  
Vitest は DOM とブラウザ API をモックするために happy-dom と jsdom の両方をサポートしています。しかし Vitest には付属していないため、別途インストールする必要があります。

```shell
cd frontend
npm i -D jsdom
```

**frontend/vite.config.ts**
```diff
+ /// <reference types="vitest/config" />
  import { defineConfig } from 'vite'
  import react from '@vitejs/plugin-react-swc'
  
  // https://vite.dev/config/
  export default defineConfig({
    plugins: [react()],
+   test: {
+     environment: "jsdom"
+   }
  })
```

### globals の設定
デフォルトでは、vitest は明示性を保つためにグローバル API を提供しません。もし、Jest のようにグローバル API を使用したい場合は、CLI で `--globals` オプションを指定するか、設定ファイルに `globals: true` を追加することができます。  
<br>

**frontend/vite.config.ts**
```ts
export default defineConfig({
  test: {
    globals: true,
  }
})
```
<br>

#### TypeScriptでグローバルAPIを使うための設定
TypeScript でグローバル API を使用するには、tsconfig.json の types フィールドに vitest/globals を追加します。  
`import { expect, test } from 'vitest'` のような記述が不要になります。  

```json
{
  "compilerOptions": {
    "types": ["vitest/globals"]
  }
}
```

# React Testing Library を導入する
React Testing Library は、React コンポーネントのテストを行うための DOM Testing Library に基づいたツールです。React コンポーネントに特化した API を追加しています。  
`render` や `screen` などが使えるようになる。  

```shell
cd frontend
npm install --save-dev @testing-library/react @testing-library/dom @types/react @types/react-dom
```

# jest-dom の導入と設定
jest-dom は、Jest 用にカスタムの DOM 要素マッチャーを提供する、Testing Library のための補助ライブラリです。  
`.toBeInTheDocument()` などのマッチャーが使えるようになる。

```shell
cd frontend
npm install --save-dev @testing-library/jest-dom
```
<br>

**frontend/setupTests.ts**  
テストセットアップファイルを作成し、@testing-library/jest-dom/vitest をインポートする。  
```ts
import '@testing-library/jest-dom/vitest'
```
<br>

**frontend/vite.config.ts**  
test セクションにテストセットアップファイルを追加して、テストが実行される前に setupTests.ts をロードするようにする。  
```ts
export default defineConfig({
  test: {
    setupFiles: ['./setupTests.ts'],
  }
})
```
<br>

**frontend/tsconfig.app.json**  
tsconfig.app.json に必要な型情報を追加する。  
@testing-library/jest-dom と vitest/globals の型を指定することで、テストで必要な型チェックが適切に行われます。  
```json
{
  "compilerOptions": {
    "types": [
      "vitest/globals",
      "@testing-library/jest-dom"
    ]
  },
  "include": ["./setupTests.ts"]
}
```






