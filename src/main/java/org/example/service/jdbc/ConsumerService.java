package org.example.service.jdbc;

import org.example.dao.jdbc.ConsumerEntityDaoImpl;
import org.example.dao.jdbc.CustomFilter;
import org.example.entity.Consumer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ConsumerService {

    private static final ConsumerEntityDaoImpl cons = new ConsumerEntityDaoImpl();

    public Consumer add(Consumer consumer, Connection c) {
        return cons.save(consumer, c);
    }

    Consumer update(Consumer consumer, Connection connection) {
        try {
            return cons.update(consumer, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Consumer> getAll(Connection connection) {
        try {
            return cons.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Consumer> getAllWithFilter(CustomFilter cust, Connection connection) {
        try {
            return cons.findAllWithFilter(cust, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Consumer getById(Long id, Connection connection) {
        return cons.findById(id, connection);
    }

    boolean deleteById(Long id, Connection connection) {
        try {
            return cons.deleteById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
