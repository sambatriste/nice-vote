package com.nablarch.example.dto;

import java.io.Serializable;

/**
 * プロジェクト検索のDto
 *
 * @author Nabu Rakutaro
 */
public class ProjectSearchDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客ID */
    private Integer clientId;

    /** プロジェクト名 */
    private String projectName;

    /**
     * 顧客IDを取得する。
     *
     * @return 顧客ID
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 顧客IDを設定する。
     *
     * @param clientId 顧客ID
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * プロジェクト名を返却する。
     *
     * @return プロジェクト名
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名を設定する。
     *
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
