package org.example.dao;

import org.example.entity.Consumer;
import org.example.utils.CustomException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ConsDao {
    List<Consumer> findAllConsumer() throws SQLException;
    List<Consumer> findAllConsumer(CustomFilter customFilter) throws SQLException;
    Consumer save(Consumer consumer) throws SQLException;
    Consumer findConsumerById(int id) throws SQLException;
    boolean  deleteById(int id) throws SQLException;
    Consumer update(Consumer consumer) throws SQLException;
}
