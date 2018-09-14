package com.tiaretdevgroup.openhackathon.java.dao;

import com.tiaretdevgroup.openhackathon.java.utils.Constants;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {


    private static final String DB_LOCATION = Constants.FILE_BASE + "\\App\\database.db";
    private static final String CREATE_TABLE = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " username TEXT NOT NULL," +
            " password TEXT NOT NULL);";

    static Connection getConnection() {
        Connection con = null;

        try {

            Class.forName("org.sqlite.JDBC");
            if (!new File(DB_LOCATION).exists()) {
                con = DriverManager.getConnection("jdbc:sqlite:" + DB_LOCATION);
                con.createStatement().execute(CREATE_TABLE);
            } else {
                con = DriverManager.getConnection("jdbc:sqlite:" + DB_LOCATION);
            }

            System.out.println("Connected !");
        } catch (SQLException se) {
            System.out.println("Error msg(SQL): " + se.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Error msg(ClassNotFound): " + ex.getMessage());
        }

        return con;
    }
}
