package com.nablarch.example.form;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link ProjectForm#isValidProjectPeriod()}のテスト。
 */
@RunWith(Theories.class)
public class ProjectFormTestIsValidProjectPeriod {
    
    @DataPoints
    public static Fixture[] fixtures = {
            new Fixture("", "", true),
            new Fixture("", "20150101", true),
            new Fixture("20150101", "", true),
            new Fixture("20150101", "20150102", true),
            new Fixture("20150102", "20150101", false)
    };

    @Theory
    public void プロジェクト期間の相関バリデーションのテスト(Fixture fixture) throws Exception {
        final ProjectForm form = new ProjectForm();
        form.setProjectStartDate(fixture.start);
        form.setProjectEndDate(fixture.end);

        Assert.assertThat(form.isValidProjectPeriod(), CoreMatchers.is(fixture.expected));
    }

    private static class Fixture {
        private final String start;
        private final String end;
        private final boolean expected;

        public Fixture(final String start, final String end, final boolean expected) {
            this.start = start;
            this.end = end;
            this.expected = expected;
        }

        @Override
        public String toString() {
            return "Fixture{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }
    }
}