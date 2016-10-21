package com.nablarch.example.code;

/**
 * 本Exampleで使用するプロジェクト種類を定義したEnum。
 *
 * @author Nabu Rakutaro
 */
public enum ProjectType implements CodeEnum {
    /** 新規開発PJ */
    DEVELOPMENT("development", "新規開発PJ"),
    /** 保守PJ */
    MAINTENANCE("maintenance", "保守PJ");

    /** プロジェクト種類のラベル */
    private String label;
    /** プロジェクト種類のコード */
    private String code;

    /**
     * コンストラクタ。
     * @param code コード値
     * @param label ラベル
     */
    ProjectType(String code, String label) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }
}
