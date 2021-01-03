package com.m4bank.dvs.db;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getMyConnection() throws SQLException, ClassNotFoundException {
        // Using Oracle
        // You may be replaced by other Database.
        return OracleConnUtils.getOracleConnection();
    }

}
