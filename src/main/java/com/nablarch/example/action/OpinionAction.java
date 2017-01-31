package com.nablarch.example.action;

import com.nablarch.example.entity.Opinion;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;
import nablarch.fw.web.HttpResponse;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

/**
 * Created by kawasaki on 1/16/17.
 */
public class OpinionAction {

    /**
     * 意見を保存する。
     *
     * @param form フォーム
     * @return レスポンス
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Valid
    public HttpResponse save(OpinionRegisterForm form) {
        Opinion opinion = BeanUtil.createAndCopy(Opinion.class, form);
        UniversalDao.insert(opinion);
        return new HttpResponse(201);
    }


    public static class OpinionRegisterForm implements Serializable {

        @Required
        @Domain("id")
        private String themeId;

        @Required
        @Domain("opinionDescription")
        private String description;

        public String getThemeId() {
            return themeId;
        }

        public void setThemeId(String themeId) {
            this.themeId = themeId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
