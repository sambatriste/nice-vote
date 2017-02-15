package com.nablarch.example.action;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.nablarch.example.DatabaseTestSupport;
import com.nablarch.example.entity.Theme;

import nablarch.common.dao.UniversalDao;
import nablarch.fw.web.MockHttpRequest;

/**
 * Created by tie301686 on 2017/02/15.
 */
public class ThemeActionTest extends DatabaseTestSupport {

    @Test
    public void testFind() {
        {
            List<Theme> themes = new ArrayList<>();
            {
                Theme theme = new Theme();
                theme.setTitle("aaa");
                themes.add(theme);
            }
            {
                Theme theme = new Theme();
                theme.setTitle("bbb");
                themes.add(theme);
            }
            UniversalDao.batchInsert(themes);
        }

        ThemeAction sut = new ThemeAction();
        MockHttpRequest request = new MockHttpRequest();
        List<Theme> themes = sut.findAll(request);
        assertThat(themes.size(), is(2));
        {
            Theme theme = themes.get(0);
            assertThat(theme.getTitle(), is("aaa"));
        }
    }
}