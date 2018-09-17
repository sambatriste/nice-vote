package com.nablarch.example.action;

import com.nablarch.example.dto.Agreements;
import com.nablarch.example.dto.Opinions;
import com.nablarch.example.entity.Theme;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;
import nablarch.fw.web.HttpRequest;

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
     * @return 意見
     */
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public Opinions find(ThemeSearchForm form) {

        List<Agreements> agreements
                = UniversalDao.findAllBySqlFile(Agreements.class,
                                                "FIND_OPINIONS",
                                                form);
        Theme theme;
        if (agreements.isEmpty()) {
            theme = UniversalDao.findById(Theme.class, form.getThemeId());
        } else {
            Agreements first = agreements.get(0);
            theme = new Theme();
            theme.setThemeId(first.getThemeId());
            theme.setTitle(first.getTitle());
        }
        return new Opinions(theme, agreements);
    }

    /**
     * テーマを登録する。
     *
     * @param form フォーム
     * @return レスポンス(OK)
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public Theme save(ThemeRegisterForm form) {
        Theme theme = BeanUtil.createAndCopy(Theme.class, form);
        UniversalDao.insert(theme);
        return theme;
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
