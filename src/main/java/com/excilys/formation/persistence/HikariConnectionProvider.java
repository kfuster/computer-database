package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.management.RuntimeErrorException;
import com.excilys.formation.persistence.util.PropertyReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionProvider {
    private static HikariConnectionProvider connectionProvider;
    private static final String HIKARI_PROPERTIES = "src/main/resources/hikari.properties";
    private static HikariDataSource dataSource;
    /**
     * Constructor for HikariConnectionProvider.
     * Initializes the properties.
     */
    private HikariConnectionProvider() {
        /*Properties properties = PropertyReader.readProperties(getClass().getClassLoader().getResourceAsStream("hikari.properties"));
        if (properties.isEmpty()) {
            throw new RuntimeException("Property file null");
        }*/
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
        return dataSource.getConnection();
    }
}