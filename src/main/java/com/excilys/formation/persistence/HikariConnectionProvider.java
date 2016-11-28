package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ch.qos.logback.classic.Logger;

public class HikariConnectionProvider {
    private static HikariConnectionProvider connectionProvider;
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(HikariConnectionProvider.class);
    private static final String HIKARI_PROPERTIES = "src/main/resources/hikari.properties";
    private static DataSource dataSource;
    private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>();
    /**
     * Constructor for HikariConnectionProvider. Initializes the properties.
     */
    private HikariConnectionProvider() {
        HikariConfig config = new HikariConfig(HIKARI_PROPERTIES);
        dataSource = new HikariDataSource(config);
    }
    /**
     * Getter for the HikariConnectionProvider instance. Initializes it if null.
     * @return the instance of HikariConnectionProvider
     */
    public static HikariConnectionProvider getInstance() {
        if (connectionProvider == null) {
            connectionProvider = new HikariConnectionProvider();
        }
        return connectionProvider;
    }
    public void initConnection() {
        try {
            Connection connection = dataSource.getConnection();
            CONNECTION.set(connection);
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : initConnection : ",e);
        }
    }
    public void commit() {
        try {
            CONNECTION.get().commit();
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : commit : ",e);
        }
    }
    public void initTransaction() {
        try {
            CONNECTION.get().setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : initTransaction : ",e);
        }
    }
    public void finishTransaction() {
        try {
            CONNECTION.get().setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : finishTransaction : ",e);
        }
    }
    public void rollback() {
        try {
            CONNECTION.get().rollback();
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : rollback : ",e);
        }
    }
    public void closeConnection() {
        try {
            CONNECTION.get().close();
            CONNECTION.remove();
        } catch (SQLException e) {
            logger.error("Error HikariConnectionProvider : closeConnection : ",e);
        }
    }
    public Connection getConnection() throws SQLException {
        return CONNECTION.get();
    }
    
}