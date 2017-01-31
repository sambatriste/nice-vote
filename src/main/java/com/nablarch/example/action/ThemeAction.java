package com.nablarch.example.action;

import com.nablarch.example.dto.OpinionAndAgreements;
import com.nablarch.example.entity.Theme;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class ThemeAction {

    /**
     * テーマを全件検索する。
     *
     * @param req HTTPリクエスト
     * @return テーマ
     */
    @Produces(MediaType.APPLICATION_JSON)
    public List<Theme> findAll(HttpRequest req) {
        return UniversalDao.findAll(Theme.class);
    }

    /**
     * 指定されたテーマについての意見を検索する。
     *
     * @param req リクエスト
     * @return 意見
     */
    @Produces(MediaType.APPLICATION_JSON)
    public List<OpinionAndAgreements> find(HttpRequest req) {
        ThemeSearchForm form = BeanUtil.createAndCopy(ThemeSearchForm.class, req.getParamMap());
        ValidatorUtil.validate(form);
        return UniversalDao.findAllBySqlFile(OpinionAndAgreements.class,
                                             "FIND_OPINIONS",
                                             form);
    }

    /**
     * テーマを登録する。
     *
     * @param form フォーム
     * @return レスポンス(OK)
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Valid
    public HttpResponse save(ThemeRegisterForm form) {
        Theme theme = BeanUtil.createAndCopy(Theme.class, form);
        UniversalDao.insert(theme);
        return new HttpResponse(201);
    }

    public static class ThemeRegisterForm implements Serializable {
        @Domain("themeTitle")
        @Required
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ThemeSearchForm implements Serializable {
        @Domain("id")
        @Required
        private String themeId;

        public String getThemeId() {
            return themeId;
        }

        public void setThemeId(String themeId) {
            this.themeId = themeId;
        }
    }
}
