package org.example.util;

import com.mysql.cj.Session;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerTest {
    private static final MySQLContainer<?> mysql = new MySQLContainer<>();

    static {
        mysql.start();
    }

    public static Connection buildConnection() {
        try {
            return DriverManager.getConnection(
                    mysql.getJdbcUrl(),
                    mysql.getUsername(),
                    mysql.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
