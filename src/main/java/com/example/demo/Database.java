package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * This class is responsible for connecting to the database and executing the SQL queries
 * It uses the Database class to connect to the database
 * It uses the url, user, and password to connect to the database
 * We maintain generic methods to insert data into the database and create tables
 * We provide the SQL query as a string to these methods
 * You can reuse this class to connect to any database
 */
public class Database {

    private String url;  // "jdbc:sqlite:TeenPregnant.sqlite" JDBC is the Java Database Connectivity API
    private String user;  // "root" for some databases you need to provide the username
    private String password; // "password" for some databases you need to provide the password


    /**
     * Constructor
     * @param url
     * @param user
     * @param password
     */
    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Constructor
     * @param url
     */
    public Database(String url) {
        this.url = url;
    }

    /**
     * Insert data into the database
     * @param sql
     *
     * SQL (Structured Query Language) is a domain-specific language used in programming
     * and designed for managing data held in a relational database management system (RDBMS),
     * or for stream processing in a relational data stream management system (RDSMS).
     *
     * We provide the SQL query as a string to this method.
     */
    public void insert(String sql) {
        System.out.println("Inserting using " + sql);
        // Try-with-resources to ensure the connection closes automatically
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Database transaction successful!");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        // Connection is automatically closed when the try block ends

    }

    public void createTables(String sql) {

        // Try-with-resources to ensure the connection closes automatically
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

            System.out.println("Database transaction successful!");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Additional methods to update, delete, and query the database can be added here

}