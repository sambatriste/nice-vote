package com.nablarch.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.nablarch.example.action.ThemeAction;
import com.nablarch.example.entity.Theme;
import com.nablarch.example.testing.http.MockHttpRequest;

import nablarch.common.dao.UniversalDao;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class HogeTest extends DatabaseTestSupport {

//    @Before
//    public void setUpDb() throws Exception {
//
//        DataSetBuilder builder = new DataSetBuilder();
//        builder.newRow("AGREEMENT")
//               .with("AGREEMENT_ID", "1").with("OPINION_ID", "1")
//               .add()
//               .newRow("AGREEMENT")
//               .with("AGREEMENT_ID", "2").with("OPINION_ID", "2")
//               .add();
//        replace(builder.build());
//    }

//    @Test
//    public void test() throws DatabaseUnitException, SQLException {
//
//        DataSetBuilder builder = new DataSetBuilder();
//        IDataSet expected = builder.newRow("AGREEMENT")
//                                   .with("AGREEMENT_ID", "1").with("OPINION_ID", "1")
//                                   .add()
//                                   .newRow("AGREEMENT")
//                                   .with("AGREEMENT_ID", "2").with("OPINION_ID", "2")
//                                   .add()
//                                   .build();
//        assertEquals(expected);
//
//    }

    @Test
    public void test3() throws Exception {
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
