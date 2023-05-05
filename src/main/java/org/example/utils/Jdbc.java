package org.example.utils;

import java.sql.*;

//public class Jdbc {
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
//
//    static final String USER = "root";
//    static final String PASS = "root";
//

//    public static Connection getConnection() throws SQLException {
//    Connection connection = null;
//
//        try {
//            // Register JDBC driver
//            Class.forName(JDBC_DRIVER);
//
//            //STEP 3: Connecting to database, Open a connection
//            System.out.println("Connecting to database...");
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//            // Clean-up environment
//            public static void closeResultSet(ResultSet resultSet) {
//                try {
//                    if (resultSet != null) {
//                        resultSet.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//    public static void closeStatement(Statement statement) {
//        try {
//            if (statement != null) {
//                statement.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void closeConnection(Connection conn) {
//        try {
//            if (conn != null) {
//                conn.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
