nablarch-example-rest
===============
Nablarchを使ったRESTfulウェブサービスのExampleです。

## 実行手順

### 1.動作環境
実行環境に以下のソフトウェアがインストールされている事を前提とします。
* Java Version : 8
* Maven 3.0.5以降

以下は、本手順では事前準備不要です。

|ソフトウェア|説明|
|:---|:---|
|APサーバ|このアプリケーションはTomcat8を使用しています。waitt-maven-pluginはTomcat8へのアプリケーションのデプロイ、起動を行います。（起動前にエンティティクラスの作成とアプリケーションのコンパイルを別途行う必要があります。）|
|DBサーバ|このアプリケーションはH2 Database Engine(以下H2)を組み込んであるため、別途インストールの必要はありません。|

### 2. プロジェクトリポジトリの取得
Gitを使用している場合、アプリケーションを配置したいディレクトリにて「git clone」コマンドを実行してください。
以下、コマンドの例です。

    $mkdir c:\example
    $cd c:\example
    $git clone https://github.com/nablarch/nablarch-example-rest.git

Gitを使用しない場合、最新のタグからzipをダウンロードし、任意のディレクトリへ展開してください。

### 3. アプリケーションのビルド
次に、データベースのセットアップ及びアプリケーションのビルドを行います。以下のコマンドを実行してください。

    $cd nablarch-example-rest
    $mvn -P gsp generate-resources
    $mvn compile

### 4  アプリケーションの起動
最後にwaitt-maven-pluginを実行し、ウェブサービスを起動します。以下のコマンドを実行してください。

    $mvn waitt:run-headless

### 5. テスト用クライアントクラスからのアクセス

以下のクラスのmainメソッドを実行します。

* com.nablarch.example.client.ProjectClient

初期状態のデータでは、標準出力に以下の内容が表示されればOKです。

    ---- projects (size: 10) ----
    Project(ProjectId: 1, ProjectName: プロジェクト００１, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 1, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 2, ProjectName: プロジェクト００２, ProjectType: maintenance, ProjectClass: b, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 2, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: null, CostOfGoodsSold: null, Sga: null, AllocationOfCorpExpenses: null, Version: 10, Client: null, SystemAccount: null)
    Project(ProjectId: 3, ProjectName: プロジェクト００３, ProjectType: development, ProjectClass: c, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 3, ProjectManager: 田中, ProjectLeader: 佐藤, UserId: 100, Note: 空白, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 4, ProjectName: プロジェクト００４, ProjectType: development, ProjectClass: a, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Mon Apr 01 00:00:00 JST 2013, ClientId: 4, ProjectManager: 山田, ProjectLeader: 田中, UserId: 100, Note: なし, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 2, Client: null, SystemAccount: null)
    Project(ProjectId: 5, ProjectName: プロジェクト００５, ProjectType: maintenance, ProjectClass: ss, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Wed Dec 31 00:00:00 JST 2014, ClientId: 5, ProjectManager: 鈴木, ProjectLeader: 山田, UserId: 100, Note: テスト, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 30, Client: null, SystemAccount: null)
    Project(ProjectId: 6, ProjectName: プロジェクト００６, ProjectType: maintenance, ProjectClass: d, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Thu Jan 08 00:00:00 JST 2015, ClientId: 6, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 100, Client: null, SystemAccount: null)
    Project(ProjectId: 7, ProjectName: プロジェクト００７, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 7, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄１, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 8, ProjectName: プロジェクト００８, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 8, ProjectManager: 佐藤, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄２, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 9, ProjectName: プロジェクト００９, ProjectType: development, ProjectClass: b, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 9, ProjectManager: 田中, ProjectLeader: 鈴木, UserId: 100, Note: 備考欄３, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 10, ProjectName: プロジェクト０１０, ProjectType: maintenance, ProjectClass: c, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Mon Apr 01 00:00:00 JST 2013, ClientId: 10, ProjectManager: 山田, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄４, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    ---- projects (size: 1) ----
    Project(ProjectId: 1, ProjectName: プロジェクト００１, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 1, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    insert status:201
    ---- projects (size: 11) ----
    Project(ProjectId: 1, ProjectName: プロジェクト００１, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 1, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 2, ProjectName: プロジェクト００２, ProjectType: maintenance, ProjectClass: b, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 2, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: null, CostOfGoodsSold: null, Sga: null, AllocationOfCorpExpenses: null, Version: 10, Client: null, SystemAccount: null)
    Project(ProjectId: 3, ProjectName: プロジェクト００３, ProjectType: development, ProjectClass: c, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 3, ProjectManager: 田中, ProjectLeader: 佐藤, UserId: 100, Note: 空白, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 4, ProjectName: プロジェクト００４, ProjectType: development, ProjectClass: a, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Mon Apr 01 00:00:00 JST 2013, ClientId: 4, ProjectManager: 山田, ProjectLeader: 田中, UserId: 100, Note: なし, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 2, Client: null, SystemAccount: null)
    Project(ProjectId: 5, ProjectName: プロジェクト００５, ProjectType: maintenance, ProjectClass: ss, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Wed Dec 31 00:00:00 JST 2014, ClientId: 5, ProjectManager: 鈴木, ProjectLeader: 山田, UserId: 100, Note: テスト, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 30, Client: null, SystemAccount: null)
    Project(ProjectId: 6, ProjectName: プロジェクト００６, ProjectType: maintenance, ProjectClass: d, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Thu Jan 08 00:00:00 JST 2015, ClientId: 6, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 100, Client: null, SystemAccount: null)
    Project(ProjectId: 7, ProjectName: プロジェクト００７, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 7, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄１, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 8, ProjectName: プロジェクト００８, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 8, ProjectManager: 佐藤, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄２, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 9, ProjectName: プロジェクト００９, ProjectType: development, ProjectClass: b, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 9, ProjectManager: 田中, ProjectLeader: 鈴木, UserId: 100, Note: 備考欄３, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 10, ProjectName: プロジェクト０１０, ProjectType: maintenance, ProjectClass: c, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Mon Apr 01 00:00:00 JST 2013, ClientId: 10, ProjectManager: 山田, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄４, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 11, ProjectName: プロジェクト９９９, ProjectType: development, ProjectClass: s, ProjectStartDate: Fri Jan 01 00:00:00 JST 2016, ProjectEndDate: Thu Mar 31 00:00:00 JST 2016, ClientId: 10, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: null, Note: 備考９９９, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 0, Client: null, SystemAccount: null)
    update status:200
    ---- projects (size: 11) ----
    Project(ProjectId: 1, ProjectName: プロジェクト００１, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 1, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 2, ProjectName: プロジェクト００２, ProjectType: maintenance, ProjectClass: b, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 2, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: null, CostOfGoodsSold: null, Sga: null, AllocationOfCorpExpenses: null, Version: 10, Client: null, SystemAccount: null)
    Project(ProjectId: 3, ProjectName: プロジェクト００３, ProjectType: development, ProjectClass: c, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 3, ProjectManager: 田中, ProjectLeader: 佐藤, UserId: 100, Note: 空白, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 4, ProjectName: プロジェクト００４, ProjectType: development, ProjectClass: a, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Mon Apr 01 00:00:00 JST 2013, ClientId: 4, ProjectManager: 山田, ProjectLeader: 田中, UserId: 100, Note: なし, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 2, Client: null, SystemAccount: null)
    Project(ProjectId: 5, ProjectName: プロジェクト００５, ProjectType: maintenance, ProjectClass: ss, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Wed Dec 31 00:00:00 JST 2014, ClientId: 5, ProjectManager: 鈴木, ProjectLeader: 山田, UserId: 100, Note: テスト, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 30, Client: null, SystemAccount: null)
    Project(ProjectId: 6, ProjectName: プロジェクト００６, ProjectType: maintenance, ProjectClass: d, ProjectStartDate: Fri Jun 22 00:00:00 JST 2012, ProjectEndDate: Thu Jan 08 00:00:00 JST 2015, ClientId: 6, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: 100, Note: null, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 100, Client: null, SystemAccount: null)
    Project(ProjectId: 7, ProjectName: プロジェクト００７, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Dec 01 00:00:00 JST 2012, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 7, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄１, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 8, ProjectName: プロジェクト００８, ProjectType: development, ProjectClass: s, ProjectStartDate: Sat Sep 18 00:00:00 JST 2010, ProjectEndDate: Mon Nov 10 00:00:00 JST 2014, ClientId: 8, ProjectManager: 佐藤, ProjectLeader: 佐藤, UserId: 100, Note: 備考欄２, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 9, ProjectName: プロジェクト００９, ProjectType: development, ProjectClass: b, ProjectStartDate: Thu Apr 09 00:00:00 JST 2015, ProjectEndDate: Thu Apr 09 00:00:00 JST 2015, ClientId: 9, ProjectManager: 田中, ProjectLeader: 鈴木, UserId: 100, Note: 備考欄３, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 1, Client: null, SystemAccount: null)
    Project(ProjectId: 10, ProjectName: プロジェクト８８８, ProjectType: development, ProjectClass: a, ProjectStartDate: Thu Jan 01 00:00:00 JST 2015, ProjectEndDate: Tue Mar 31 00:00:00 JST 2015, ClientId: 1, ProjectManager: 佐藤, ProjectLeader: 鈴木, UserId: null, Note: 備考８８８, Sales: 20000, CostOfGoodsSold: 2000, Sga: 3000, AllocationOfCorpExpenses: 4000, Version: 2, Client: null, SystemAccount: null)
    Project(ProjectId: 11, ProjectName: プロジェクト９９９, ProjectType: development, ProjectClass: s, ProjectStartDate: Fri Jan 01 00:00:00 JST 2016, ProjectEndDate: Thu Mar 31 00:00:00 JST 2016, ClientId: 10, ProjectManager: 鈴木, ProjectLeader: 佐藤, UserId: null, Note: 備考９９９, Sales: 10000, CostOfGoodsSold: 1000, Sga: 2000, AllocationOfCorpExpenses: 3000, Version: 0, Client: null, SystemAccount: null)

### 6. テーブルのデータの初期化手順
テーブルのデータを初期状態に戻したい場合は、以下のコマンドを実行します。

    $mvn gsp-dba:execute-ddl

### 7. DBの確認方法

1. http://www.h2database.com/html/cheatSheet.html からH2をインストールしてください。

2. {インストールフォルダ}/bin/h2.bat を実行してください(コマンドプロンプトが開く)。  
  ※h2.bat実行中はExampleアプリケーションからDBへアクセスすることができないため、Exampleアプリケーションを停止しておいてください。

3. ブラウザから http://localhost:8082 を開き、以下の情報でH2コンソールにログインしてください。
x   JDBC URLの{dbファイルのパス}には、`rest_example.mv.db`ファイルの格納ディレクトリまでのパスを指定してください。  
  JDBC URL：jdbc:h2:{dbファイルのパス}/rest_example  
  ユーザ名：REST_EXAMPLE  
  パスワード：REST_EXAMPLE
