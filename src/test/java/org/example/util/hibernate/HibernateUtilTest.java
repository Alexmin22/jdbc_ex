package org.example.util.hibernate;

import org.example.utils.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.testcontainers.containers.MySQLContainer;

public class HibernateUtilTest {

    private static final MySQLContainer<?> mysql = new MySQLContainer<>();

    static {
        mysql.start();
    }

    public static SessionFactory buildSessionFactory() {
        final var configuration = HibernateUtil.buildConfiguration();
        configuration.setProperty("hibernate.connection.url", mysql.getJdbcUrl());
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
