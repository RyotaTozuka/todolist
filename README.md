# todolist

web上で利用するtodoリストのアプリケーション


### 環境
DB：デフォルトでは、postgreSQL（version 11.5）を想定  
※実行するには、postgreSQL内に「todolist」というDATABASEを作成する必要あり  

* 言語：java
* FW：spring boot
* build tool：maven
* ORマッパー：doma
* htmlテンプレート：thymeleaf

### あらかじめ登録されているユーザ
* username：Tozuka, password：pass, 権限：管理者
* username：Tanaka, password：password, 権限：一般ユーザ

### 実装した機能
* ログイン認証機能
* ユーザ作成機能
* パスワード変更機能
* Todoリスト新規作成/編集/削除機能
  (未完了のリストと完了したリストは画面を分けている)
* 管理者用機能
  * ユーザ一覧表示/権限変更/削除機能

### 入力チェック等
* usernameは重複を許さない
* usernameは1~20文字
* passwordは文字数制限特になし
* Todoリストの「内容」は、1~30文字
* Todoリストの「期限」は、未入力でもよい（"期限なし"と表示される）

### 未実装
* ユニットテスト
* 開発者ツールで値を改ざんされることを想定したエラーハンドリング

### 参考にしたメディア
* [本：Webを支える技術](https://www.amazon.co.jp/Web%E3%82%92%E6%94%AF%E3%81%88%E3%82%8B%E6%8A%80%E8%A1%93-HTTP%E3%80%81URI%E3%80%81HTML%E3%80%81%E3%81%9D%E3%81%97%E3%81%A6REST-WEB-PRESS-plus/dp/4774142042)
* [Webページ：spring-boot](https://spring.io/projects/spring-boot)
* [Webページ：Domaプロジェクト](http://doma.seasar.org/index.html)
* [Webページ：Tutorial: Using Thymeleaf (ja)](https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf_ja.html)
* [Webページ：SpringBootとDoma2を使用したサンプルアプリケーション](https://springboot-domamaster-maintenance-sample.readthedocs.io/ja/latest/index.html)
* 他、Qiita等の記事多数
