package org.example.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface EntityDao<K, V> { // K/V

    V save(V v, Connection connection) throws SQLException;

    V update(V v, Connection connection) throws SQLException;
    List<V> findAll(Connection connection) throws SQLException;

    V findById(K k, Connection connection) throws SQLException;

    boolean deleteById(K k, Connection connection) throws SQLException;
}
