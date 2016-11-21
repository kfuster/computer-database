package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.excilys.formation.persistence.util.PropertyReader;

/**
 * Manages DB connection.
 * @author kfuster
 *
 */
public class JdbcConnectionProvider {
    private static JdbcConnectionProvider connectionProvider;
    private Connection connection;
    private Properties properties;
    private static final String CONNECTION_PROPERTIES = "connection.properties";
    /**
     * Constructor for ConnectionProvider.
     * Initializes the properties.
     */
    private JdbcConnectionProvider() {
        ClassLoader classLoader = getClass().getClassLoader();
        properties = PropertyReader.readProperties(classLoader.getResourceAsStream(CONNECTION_PROPERTIES));
    }
    /**
     * Getter for the ConnectionProvider instance.
     * Initializes it if null.
     * @return the instance of ConnectionProvider
     */
    public static JdbcConnectionProvider getInstance() {
        if (connectionProvider == null) {
            connectionProvider = new JdbcConnectionProvider();
        }
        return connectionProvider;
    }
    /**
     * Open the connection.
     */
    public void openConnection() {
        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Close the connection.
     */
    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
}