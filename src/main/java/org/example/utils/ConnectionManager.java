package org.example.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {
    private static final String JDBC_DRIVER = "db.driver";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnection;


    static {
        initConnectionPool();
        loadDriver();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.getProperty(POOL_SIZE_KEY);
        int size = poolSize == null? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnection = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = openConnection();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    new Class[]{Connection.class}, (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnection.add(connection);

        }
    }
    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Connection pool throws exception", e);
        }
    }

    private static Connection openConnection() {
        try {
            Connection connection = DriverManager.getConnection(PropertiesUtil.getProperty(URL_KEY),
                    PropertiesUtil.getProperty(USERNAME_KEY), PropertiesUtil.getProperty(PASSWORD_KEY));
            return connection;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no such JDBC_DRIVER");
        }
    }

    public static void closePool() {
            try {
                for (Connection c : sourceConnection) {
                c.close();
            }
        } catch (SQLException e) {
                throw new RuntimeException("Can't close connection", e);
            }
    }
}
