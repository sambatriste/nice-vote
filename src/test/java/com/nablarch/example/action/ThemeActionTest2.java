package com.nablarch.example.action;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.nablarch.example.action.ThemeAction.ThemeSearchForm;
import com.nablarch.example.dto.OpinionAndAgreements;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nablarch.common.dao.EntityList;
import nablarch.common.dao.UniversalDao;

@RunWith(JMockit.class)
public class ThemeActionTest2 {
    @Mocked
    UniversalDao universalDao;

    @Test
    public void test() {

        new Expectations() {{
            List<OpinionAndAgreements> ret = new EntityList<>();

            OpinionAndAgreements o = new OpinionAndAgreements();
            o.setTitle("aaa");
            o.setAgreementCount(1);
            o.setDescription("aaa is aaa");
            ret.add(o);

            UniversalDao.findAllBySqlFile(OpinionAndAgreements.class,
                                          "FIND_OPINIONS",
                                          any);result = ret;
        }};

        ThemeAction sut = new ThemeAction();
        List<OpinionAndAgreements> actual = sut.find(new ThemeSearchForm());
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getTitle(), is("aaa"));
    }
}
