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
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HikariConnectionProvider.class);
    private static final String HIKARI_PROPERTIES = "src/main/resources/hikari.properties";
    private static DataSource dataSource;
    private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>() {
        public Connection initialValue() {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                logger.error("Error CompanyDao : ThreadLocal initialValue : ", e);
            }
            return null;
        }
    };
    /**
     * Constructor for HikariConnectionProvider.
     * Initializes the properties.
     */
    private HikariConnectionProvider() {
        HikariConfig config = new HikariConfig(HIKARI_PROPERTIES);
        dataSource = new HikariDataSource(config);
    }
    /**
     * Getter for the HikariConnectionProvider instance.
     * Initializes it if null.
     * @return the instance of HikariConnectionProvider
     */
    public static HikariConnectionProvider getInstance() {
        if (connectionProvider == null) {
            connectionProvider = new HikariConnectionProvider();
        }
        return connectionProvider;
    }
    public Connection getConnection () throws SQLException {
        return CONNECTION.get();
    }
}