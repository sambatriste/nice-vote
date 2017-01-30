package com.nablarch.example;

import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.nablarch.example.entity.Agreement;

import nablarch.common.dao.EntityList;
import nablarch.common.dao.UniversalDao;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class HogeTest {

    @ClassRule
    public static SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource();

    @Rule
    public DatabaseResource databaseResource = new DatabaseResource();

    @Before
    public void setUpDb() throws Exception {

        DataSetBuilder builder = new DataSetBuilder();
        builder.newRow("AGREEMENT")
               .with("AGREEMENT_ID", "1").with("OPINION_ID", "1")
               .add()
               .newRow("AGREEMENT")
               .with("AGREEMENT_ID", "2").with("OPINION_ID", "2")
               .add();

        IDatabaseTester tester = new DataSourceDatabaseTester(databaseResource.getDataSource());
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(builder.build());
        tester.onSetup();

    }

    @Test
    public void test() throws DatabaseUnitException, SQLException {

        IDatabaseConnection connection = new DatabaseConnection(databaseResource.getConnection());
        ITable actual = connection.createTable("AGREEMENT");

        DataSetBuilder builder = new DataSetBuilder();
        ITable expected = builder.newRow("AGREEMENT")
                                 .with("AGREEMENT_ID", "2").with("OPINION_ID", "1")
                                 .add()
                                 .build()
                                 .getTable("AGREEMENT");
        Assertion.assertEquals(expected, actual);

//        EntityList<Theme> all = UniversalDao.findAll(Theme.class);
//        for (Theme theme : all) {
//            System.out.println("theme.getTitle() = " + theme.getTitle());
//        }
        EntityList<Agreement> all = UniversalDao.findAll(Agreement.class);
        all.forEach(agreement -> System.out.println(agreement.getAgreementId()));



    }

}
