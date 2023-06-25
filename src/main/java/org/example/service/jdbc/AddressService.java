package org.example.service.jdbc;

import org.example.dao.jdbc.AddressConsumerHomeEntityDaoImpl;
import org.example.entity.AddressConsumerHome;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddressService {

    private static final AddressConsumerHomeEntityDaoImpl ADDRESS_CONS = new AddressConsumerHomeEntityDaoImpl();

    public AddressConsumerHome add(AddressConsumerHome address, Connection c) {
        try {
            return ADDRESS_CONS.save(address, c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    AddressConsumerHome update(AddressConsumerHome address, Connection connection) {
        try {
            return ADDRESS_CONS.update(address, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<AddressConsumerHome> getAll(Connection connection) {
        try {
            return ADDRESS_CONS.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    AddressConsumerHome getById(Long id, Connection connection) {
        try {
            return ADDRESS_CONS.findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean deleteById(Long id, Connection connection) {
        try {
            return ADDRESS_CONS.deleteById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
