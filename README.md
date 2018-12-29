# Appalachia

関数型の自作言語です

授業の課題でつくりましたがほそぼそメンテします

## 言語仕様

### リテラル

- 数値(number)
  - ex) `1`, `0.5`, `-10`
  - 整数も実数も扱えます
- 文字列(string)
  - ex) `"aaa"`, `"string"`
- ブロック(block)
  - `{}` の中に任意の式を書く
  - ex) `{1}` `{1 "abc"}`
  - ブロックは最後に評価された式の値がブロック自体の値になる
  - ブロックは別のスコープで評価される(後述)
  - this(後述)を使うとブロック自体がブロックの値になる
- 関数(lambda)
  - `\`(バックスラッシュ)のあとに引数を`,`区切り、その後に ` -> ` が続いて ブロックが続く

### 変数

英数字が変数名として有効

`number = 1`

```
f = \x -> {
  +(x, 1)
}
```

### 特殊変数/組み込み関数

#### this
現在のブロックを参照する特殊変数
