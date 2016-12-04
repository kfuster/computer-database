package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ch.qos.logback.classic.Logger;

public enum HikariConnectionProvider {
    INSTANCE;
    private static final Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(HikariConnectionProvider.class);
    private static final String HIKARI_PROPERTIES = "src/main/resources/hikari.properties";
    private static DataSource dataSource;
    private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>();
    static {
        HikariConfig config = new HikariConfig(HIKARI_PROPERTIES);
        dataSource = new HikariDataSource(config);
    }

    /**
     * Init a connection.
     * Get a connection from the pool and set it in the ThreadLocal.
     */
    public void initConnection() {
        try {
            Connection connection = dataSource.getConnection();
            CONNECTION.set(connection);
        } catch (SQLException e) {
            LOGGER.error("Error HikariConnectionProvider : initConnection : ", e);
        }
    }

    /**
     * Commit the connection in the ThreadLocal.
     */
    public void commit() {
        try {
            CONNECTION.get().commit();
        } catch (SQLException e) {
            rollback();
            LOGGER.error("Error HikariConnectionProvider : commit : ", e);
        }
    }

    /**
     * Init a transaction.
     * Set autoCommit at false for the connection in the ThreadLocal.
     */
    public void initTransaction() {
        try {
            CONNECTION.get().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Error HikariConnectionProvider : initTransaction : ", e);
        }
    }

    /**
     * Ends a transaction.
     * Set autoCommit at true for the connection in the ThreadLocal.
     */
    public void finishTransaction() {
        try {
            CONNECTION.get().setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Error HikariConnectionProvider : finishTransaction : ", e);
        }
    }

    /**
     * Do a rollback for the connection in the ThreadLocal.
     */
    public void rollback() {
        try {
            CONNECTION.get().rollback();
        } catch (SQLException e) {
            LOGGER.error("Error HikariConnectionProvider : rollback : ", e);
        }
    }

    /**
     * Close the connection in the ThreadLocal.
     * Once closed, invoke remove on the ThreadLocal.
     */
    public void closeConnection() {
        try {
            CONNECTION.get().close();
            CONNECTION.remove();
        } catch (SQLException e) {
            LOGGER.error("Error HikariConnectionProvider : closeConnection : ", e);
        }
    }

    /**
     * Get the connection from the ThreadLocal.
     * @return a Connection.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return CONNECTION.get();
    }

    /**
     * Get a Connection from the DataSource.
     * @return a Connection.
     * @throws SQLException
     */
    public Connection getConnectionDataSource() throws SQLException {
        return dataSource.getConnection();
    }
}