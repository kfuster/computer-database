package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formation.persistence.util.PropertyReader;

/**
 * Manages DB connection
 * 
 * @author kfuster
 *
 */
public class ConnectionProvider {
    private static ConnectionProvider connectionProvider;
    private Connection connection;
    private Properties properties;
    private static final String CONNECTION_PROPERTIES = "connection.properties";
    
    private ConnectionProvider() {
        ClassLoader classLoader = getClass().getClassLoader();
        properties = PropertyReader.readProperties(classLoader.getResourceAsStream(CONNECTION_PROPERTIES));
    }

    public static ConnectionProvider getInstance(){
        if(connectionProvider == null){
            connectionProvider = new ConnectionProvider();
        }
        
        return connectionProvider;
    }

    /**
     * Open the connection
     */
    public void openConnection() {
        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"), properties.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection
     */
    public void closeConnection() {
        try {
            if(!connection.isClosed()) {
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
