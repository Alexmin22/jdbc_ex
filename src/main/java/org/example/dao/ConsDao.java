package org.example.dao;

import org.example.entity.Consumer;

import java.sql.SQLException;
import java.util.List;

public interface ConsDao {
    List<Consumer> findAllConsumer() throws SQLException;
    void save(Consumer consumer) throws SQLException;
    Consumer findConsumerById(int id) throws SQLException;
    void  deleteById(int id) throws SQLException;
    void update(Consumer consumer) throws SQLException;
}
