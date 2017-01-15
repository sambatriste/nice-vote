package com.nablarch.example.action;

import com.nablarch.example.entity.Theme;
import com.nablarch.example.form.ProjectSearchForm;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
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
    public Theme find(HttpRequest req) {
        ThemeSearchForm form = BeanUtil.createAndCopy(ThemeSearchForm.class, req.getParamMap());

        // BeanValidation実行
        ValidatorUtil.validate(form);

        return UniversalDao.findById(Theme.class, form.getThemeId());
    }

    public static class ThemeSearchForm implements Serializable {
        private int themeId;

        public int getThemeId() {
            return themeId;
        }

        public void setThemeId(int themeId) {
            this.themeId = themeId;
        }
    }
}
