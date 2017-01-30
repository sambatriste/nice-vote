package com.nablarch.example;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class HogeTest extends DatabaseTestSupport {

    @Before
    public void setUpDb() throws Exception {

        DataSetBuilder builder = new DataSetBuilder();
        builder.newRow("AGREEMENT")
               .with("AGREEMENT_ID", "1").with("OPINION_ID", "1")
               .add()
               .newRow("AGREEMENT")
               .with("AGREEMENT_ID", "2").with("OPINION_ID", "2")
               .add();
        replace(builder.build());
    }

    @Test
    public void test() throws DatabaseUnitException, SQLException {

        DataSetBuilder builder = new DataSetBuilder();
        IDataSet expected = builder.newRow("AGREEMENT")
                                   .with("AGREEMENT_ID", "1").with("OPINION_ID", "1")
                                   .add()
                                   .newRow("AGREEMENT")
                                   .with("AGREEMENT_ID", "2").with("OPINION_ID", "2")
                                   .add()
                                   .build();
        assertEquals(expected);

    }

}
