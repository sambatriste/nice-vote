package com.nablarch.example.action;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.nablarch.example.action.OpinionAction.OpinionRegisterForm;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nablarch.common.dao.UniversalDao;
import nablarch.fw.web.HttpResponse;

/**
 * Created by tie301686 on 2017/03/01.
 */
@RunWith(JMockit.class)
public class OpinionActionTest {

    @Mocked
    UniversalDao universalDao = null;

    @Test
    public void test() {
        new Expectations() {
            {
                UniversalDao.insert(any);
            }
        };

        OpinionAction sut = new OpinionAction();
        HttpResponse response = sut.save(new OpinionRegisterForm());
        assertThat(response.getStatusCode(), is(201));
    }


}