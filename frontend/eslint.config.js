import js from '@eslint/js'   // JavaScript 用の基本的な ESLint 設定を提供するパッケージ
import globals from 'globals'   // よく使われるグローバル変数 (例えば window や document) を定義したパッケージ
import reactHooks from 'eslint-plugin-react-hooks'   // React のフック (Hooks) の使用を正しく強制するためのプラグイン
import reactRefresh from 'eslint-plugin-react-refresh'   // React のホットリロード (React Fast Refresh) のためのプラグイン
import tseslint from 'typescript-eslint'   // TypeScript を解析するための ESLint パッケージ
import react from 'eslint-plugin-react'   // React アプリケーションのコードスタイルやベストプラクティスを強制するためのプラグイン
import jsxA11y from 'eslint-plugin-jsx-a11y'   // アクセシビリティ (a11y) を強化するためのプラグイン

export default tseslint.config(   // TypeScript 用の ESLint 設定を定義してエクスポートし、定内容をすべて tseslint.config に渡します。
  {
    ignores: [   // ESLint に無視させるパスを指定
      'dist',
      'src/**/*.d.{ts,tsx}'
    ]
  },
  {
    extends: [   // ベースとなる設定を継承します。
      js.configs.recommended,   // JavaScript の推奨設定
      ...tseslint.configs.recommended,   // TypeScript の推奨設定 (複数展開)
      jsxA11y.flatConfigs.recommended,   // JSX アクセシビリティの推奨設定
      react.configs.flat.recommended,   // React の推奨設定
      react.configs.flat['jsx-runtime'],   // React の JSX ランタイム用設定
    ],
    files: ['src/**/*.{ts,tsx}'],   // ESLint がチェック対象とするファイルのパスを指定
    languageOptions: {   // 言語の仕様や環境を指定
      ecmaVersion: 2020,   // ECMAScript 2020 の仕様を使用
      globals: globals.browser,   // ブラウザ環境のグローバル変数 (例: window, document) を設定
    },
    settings: { react: { version: 'detect' } },   // React のバージョンを自動検出します。
    plugins: {   // 使用する ESLint プラグインを登録
      'react-hooks': reactHooks,   // React Hooks 用ルールを設定
      'react-refresh': reactRefresh,   // React Refresh 用ルールを設定
    },
    rules: {
      ...reactHooks.configs.recommended.rules,   // React Hooks の推奨ルールを展開
      'react-refresh/only-export-components': [
        'warn',   // React Refresh においてコンポーネント以外のエクスポートを警告 (warn)。
        { allowConstantExport: true },   // 定数のエクスポートは許可 (allowConstantExport: true)。
      ],
    },
  },
)
