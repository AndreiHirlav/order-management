package org.example.connection;
import java.sql.*;
import java.util.logging.*;

/**
 * Used for creating the connection to the database
 */

public class ConnectionFactory {
    /**
     * Used for logging messages related to database connection errors/warnings
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     * Contains the driver class name, used for establishind a connection
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * It contains the URL for connection to the database (named warehousedb)
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehousedb";
    /**
     * The username for authenticating with the database
     */
    private static final String USER = "root";
    /**
     * The password for authenticating with the database
     */
    private static final String PASS = "parola12";

    /**
     * It ensures that only one instance of the ConnectionFactory is created - singleton pattern
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();


    /**
     * Constructor for creating a connection
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the connection to the database
     * @return the connection itself
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * It closes the connection
     * @param connection the connection itself
     */
    public static void close(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
                e.printStackTrace();
            }
        }
    }

    /**
     * It closes the statement
     * @param statement
     */
    public static void close(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
                e.printStackTrace();
            }
        }
    }

    /**
     * It closes the resultSet
     * @param resultSet
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
                e.printStackTrace();
            }
        }
    }
}
