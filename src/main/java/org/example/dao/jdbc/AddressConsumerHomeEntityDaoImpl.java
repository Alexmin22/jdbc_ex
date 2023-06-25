package org.example.dao.jdbc;

import org.example.entity.AddressConsumerHome;
import org.example.utils.jdbc.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressConsumerHomeEntityDaoImpl implements EntityDao<Long, AddressConsumerHome> {
    private static final String SAVE = "INSERT INTO address_consumer_home (city, street) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE address_consumer_home SET city = ?, street = ? WHERE id =?";
    private static final String DELETE = "DELETE FROM address_consumer_home WHERE id =?";
    private static final String FIND_ALL = "SELECT id, city, street FROM address_consumer_home";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id =?";


    @Override
    public List<AddressConsumerHome> findAll(Connection connection) throws SQLException {
        List<AddressConsumerHome> addresses = new ArrayList<>();

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                AddressConsumerHome address = AddressConsumerHome
                        .builder()
                        .id(rs.getLong("id"))
                        .city(rs.getString("city"))
                        .street(rs.getString("street"))
                        .build();
                addresses.add(address);
            }
        }
        return addresses;
    }

    @Override
    public AddressConsumerHome save(AddressConsumerHome addressConsumerHome, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, addressConsumerHome.getCity());
            statement.setString(2, addressConsumerHome.getStreet());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                addressConsumerHome.setId(generatedKeys.getLong(1));
            }
            return addressConsumerHome;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AddressConsumerHome findById(Long id, Connection connection) throws SQLException {
        AddressConsumerHome address = null;
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    address = AddressConsumerHome
                            .builder()
                            .id(rs.getLong("id"))
                            .city(rs.getString("city"))
                            .street(rs.getString("street"))
                            .build();
                } else {
                    throw new CustomException("Address with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
        return address;
    }

    public AddressConsumerHome findByIdNotClosed(Long id, Connection connection) throws SQLException {
        AddressConsumerHome address = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    address = AddressConsumerHome
                            .builder()
                            .id(rs.getLong("id"))
                            .city(rs.getString("city"))
                            .street(rs.getString("street"))
                            .build();
                } else {
                    throw new CustomException("Address with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        } finally {

        }
        return address;
    }

    @Override
    public AddressConsumerHome update(AddressConsumerHome addressConsumerHome, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, addressConsumerHome.getCity());
            statement.setString(2, addressConsumerHome.getStreet());
            statement.setLong(3, addressConsumerHome.getId());
            statement.executeUpdate();

            return findById(addressConsumerHome.getId(), connection);
        }
    }

    @Override
    public boolean deleteById(Long id, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;   //executeUpdate() вернет инт, мы переводим этот инт в тру фолс
        }
    }
}
