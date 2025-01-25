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
