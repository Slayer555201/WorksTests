package com.m4bank.dvs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.m4bank.dvs.RUN.settings;

public class OracleConnUtils {

    // Connect to Oracle.
    public static Connection getOracleConnection() throws SQLException, ClassNotFoundException {
        final String hostName = settings.getConfig("hostName", "10.31.0.250", "db");
        final String sid = settings.getConfig("sid", "cctmpos", "db");
        final String userName = settings.getConfig("userName", "DEV_API5", "db");
        final String password = settings.getConfig("password", "DEV_API5", "db");

        return getOracleConnection(hostName, sid, userName, password);
    }

    public static Connection getOracleConnection(
            final String hostName,
            final String sid,
            final String userName,
            final String password)
            throws ClassNotFoundException, SQLException {

        // Declare the class Driver for ORACLE DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        Class.forName("oracle.jdbc.driver.OracleDriver");

        String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;

        return DriverManager.getConnection(connectionURL, userName,
                password);
    }

}
