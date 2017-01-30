package com.nablarch.example;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.ClassRule;
import org.junit.Rule;

import java.sql.SQLException;

/**
 * Created by tie301686 on 2017/01/30.
 */
public class DatabaseTestSupport {

    @ClassRule
    public static SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource();

    @Rule
    public DatabaseResource databaseResource = new DatabaseResource();

    public void replace(IDataSet dataSet) throws Exception {
        IDatabaseTester tester = new DataSourceDatabaseTester(databaseResource.getDataSource());
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    public void assertEquals(ITable expected) throws DatabaseUnitException, SQLException {
        String tableName = expected.getTableMetaData().getTableName();
        IDatabaseConnection connection = new DatabaseConnection(databaseResource.getConnection());
        ITable actual = connection.createTable(tableName);
        Assertion.assertEquals(expected, actual);
    }

    public void assertEquals(IDataSet expected) throws DatabaseUnitException, SQLException {
        IDatabaseConnection connection = new DatabaseConnection(databaseResource.getConnection());
        IDataSet actual = connection.createDataSet(expected.getTableNames());
        Assertion.assertEquals(expected, actual);
    }
}
