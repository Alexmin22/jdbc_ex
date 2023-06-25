package org.example.util;

import org.example.dao.jdbc.ConsumerEntityDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDataImporterTest {

    private ConsumerEntityDaoImpl consumerEntityDao = new ConsumerEntityDaoImpl();
    private final String sql = """
            SELECT 
                *
            FROM
                roles;
            """;

    @BeforeEach
    void dataLoader() {
        System.out.println(1);
        TestDataImporter.importData();
    }
    @Test
    void test() {
        try (Connection connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {

            final var resultSet = statement.executeQuery(sql);

            resultSet.next();

            System.out.println(resultSet.getString("roles"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
