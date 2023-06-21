package org.example.dao;

import org.example.entity.Consumer;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface EntityDao<T> {

    String DELETE_SQL = "DELETE FROM ? WHERE id =?";
    T save(T t, Connection connection) throws SQLException;

    T update(T t, Connection connection) throws SQLException;
    List<T> findAll(Connection connection) throws SQLException;

    T findById(long id, Connection connection) throws SQLException;


    default boolean deleteById(long id, String tableName, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setString(1, tableName);
            statement.setLong(2, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;   //executeUpdate() вернет инт, мы переводим этот инт в тру фолс
        }
    }
}
