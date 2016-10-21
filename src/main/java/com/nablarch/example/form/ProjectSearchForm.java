package com.nablarch.example.form;

import nablarch.core.validation.ee.Domain;

import java.io.Serializable;

/**
 * プロジェクト検索フォーム
 *
 * @author Nabu Rakutaro
 */
public class ProjectSearchForm implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客ID */
    @Domain("id")
    private String clientId;

    /** プロジェクト名 */
    @Domain("projectName")
    private String projectName;

    /**
     * 顧客IDを取得する。
     * @return 顧客ID
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * 顧客IDを設定する。
     * @param clientId 顧客ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * プロジェクト名を取得する。
     * @return プロジェクト名
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * プロジェクト名を設定する。
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
