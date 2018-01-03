package com.nablarch.example.action;

import com.nablarch.example.action.ThemeAction.ThemeSearchForm;
import com.nablarch.example.dto.Agreements;
import com.nablarch.example.dto.Opinions;
import com.nablarch.example.entity.Theme;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nablarch.common.dao.EntityList;
import nablarch.common.dao.UniversalDao;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JMockit.class)
public class ThemeActionTest2 {
    @Mocked
    UniversalDao universalDao;

    @Test
    public void test() {

        new Expectations() {{

            Theme theme = new Theme();
            theme.setThemeId(1);
            theme.setTitle("aaa");
            UniversalDao.findById(Theme.class, "1");result = theme;

            List<Agreements> ret = new EntityList<>();
            Agreements a = new Agreements();
            a.setTitle("aaa");
            a.setAgreementCount(1);
            a.setDescription("aaa is aaa");
            ret.add(a);
            UniversalDao.findAllBySqlFile(Agreements.class,
                                          "FIND_OPINIONS",
                                          any);result = ret;

        }};
        ThemeAction sut = new ThemeAction();
        ThemeSearchForm form = new ThemeSearchForm();
        form.setThemeId("1");
        Opinions actual = sut.find(form);
        assertThat(actual.getTitle(), is("aaa"));
        assertThat(actual.getAgreements().size(), is(1));

    }
}
