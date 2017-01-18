package com.nablarch.example.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import nablarch.core.db.connection.BasicDbConnection;
import nablarch.core.db.connection.ConnectionFactorySupport;
import nablarch.core.db.connection.TransactionManagerConnection;

/**
 * Created by tie301686 on 2017/01/16.
 */
public class EnvironmentVariableConnectionFactory extends ConnectionFactorySupport {

    private static final String DB_URL_VAR_NAME = "JDBC_DATABASE_URL";
    private final String databaseUrl;

    public EnvironmentVariableConnectionFactory() {
        this.databaseUrl = System.getenv(DB_URL_VAR_NAME);
        if (databaseUrl == null) {
            throw new IllegalStateException("Environment variable '" + DB_URL_VAR_NAME + "' must be set.");
        }
    }

    @Override
    public TransactionManagerConnection getConnection(String connectionName) {


        try {
            BasicDbConnection dbConnection = new BasicDbConnection(DriverManager.getConnection(databaseUrl));
            initConnection(dbConnection, connectionName);
            return dbConnection;
        } catch (SQLException e) {
            throw dbAccessExceptionFactory.createDbAccessException("failed to get database connection.", e, null);
        }


    }

}
