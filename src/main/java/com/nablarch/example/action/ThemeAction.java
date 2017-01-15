package com.nablarch.example.action;

import com.nablarch.example.dto.Opinions;
import com.nablarch.example.entity.Theme;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.web.HttpRequest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class ThemeAction {
    /**
     * プロジェクト情報を検索する。
     *
     * @param req HTTPリクエスト
     * @return プロジェクト情報リスト
     */
    @Produces(MediaType.APPLICATION_JSON)
    public List<Theme> findAll(HttpRequest req) {
        return UniversalDao.findAll(Theme.class);
    }

    @Produces(MediaType.APPLICATION_JSON)
    public List<Opinions> find(HttpRequest req) {
        ThemeSearchForm form = BeanUtil.createAndCopy(ThemeSearchForm.class, req.getParamMap());

        // BeanValidation実行
        ValidatorUtil.validate(form);

        return UniversalDao.findAllBySqlFile(Opinions.class,
                                             "FIND_OPINIONS",
                                             form);
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
