package com.nablarch.example;

import nablarch.core.db.connection.ConnectionFactory;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import nablarch.core.repository.SystemRepository;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class DatabaseResource extends ExternalResource {

    private SimpleDbTransactionManager manager;

    private final List<Connection> connections = new ArrayList<>();

    @Override
    protected void before() throws Throwable {
        manager = getDefaultTransactionManager();
        manager.beginTransaction();
    }

    @Override
    protected void after() {
        manager.rollbackTransaction();
        manager.endTransaction();

        closeConnections();
    }

    /**
     * デフォルトのトランザクションマネージャを取得する。
     *
     * @return デフォルトのトランザクションマネージャ
     */
    private SimpleDbTransactionManager getDefaultTransactionManager() {
        SimpleDbTransactionManager defaultManager = new SimpleDbTransactionManager();
        defaultManager.setConnectionFactory(getConnectionFactory());
        defaultManager.setTransactionFactory(SystemRepository.get("transactionFactory"));
        return defaultManager;
    }

    private ConnectionFactory getConnectionFactory() {
        return SystemRepository.get("connectionFactory");
    }

    public DataSource getDataSource() {
        DataSource dataSource = SystemRepository.get("dataSource");
        assert dataSource != null;
        return dataSource;
    }

    public Connection getConnection() {
        Connection connection = getConnectionFactory().getConnection("").getConnection();
        connections.add(connection);
        return connection;
    }

    private void closeConnections() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

}
