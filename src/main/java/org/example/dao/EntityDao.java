package org.example.dao;

import org.example.entity.Consumer;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EntityDao<T> {

    T save(T t) throws SQLException;

    T update(T t) throws SQLException;
    List<T> findAll() throws SQLException;

    T findById(long id) throws SQLException;


    default boolean deleteById(long id, String tableName) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM ? WHERE id =?"
             )) {
            statement.setString(1, tableName);
            statement.setLong(2, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;   //executeUpdate() вернет инт, мы переводим этот инт в тру фолс
        }
    }
}
