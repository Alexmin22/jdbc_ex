package org.example.dao;

import org.example.entity.AddressConsumerHome;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressConsumerHomeEntityDaoImpl implements EntityDao<AddressConsumerHome> {
    private static final AddressConsumerHomeEntityDaoImpl INSTANCE = new AddressConsumerHomeEntityDaoImpl();
    public static AddressConsumerHomeEntityDaoImpl getInstance() {
        return INSTANCE;
    }
    private static final  String SAVE = "INSERT INTO addressconsumerhome (city, street) VALUES (?, ?)";
    private static final  String UPDATE = "UPDATE addressconsumerhome SET city = ?, street = ? WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, city, street FROM addressconsumerhome";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";



    @Override
    public List<AddressConsumerHome> findAll() throws SQLException {
        List<AddressConsumerHome> addresses = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                AddressConsumerHome addressConsumerHome = addAddress(rs);
                addresses.add(addressConsumerHome);
            }
        }
        return addresses;
    }

    private AddressConsumerHome addAddress(ResultSet rs) throws SQLException {
        return new AddressConsumerHome(rs.getInt("id"),
                rs.getString("city"),
                rs.getString("street"));
    }

    @Override
    public AddressConsumerHome save(AddressConsumerHome addressConsumerHome) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, addressConsumerHome.getCity());
            statement.setString(2, addressConsumerHome.getStreet());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                addressConsumerHome.setId(generatedKeys.getInt(1));
            }
            return addressConsumerHome;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AddressConsumerHome findById(long id) throws SQLException {
        AddressConsumerHome address = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    address = addAddress(rs);
                } else {
                    throw new CustomException("Consumer with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
        return address;
    }

    @Override
    public AddressConsumerHome update(AddressConsumerHome addressConsumerHome) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, addressConsumerHome.getCity());
            statement.setString(2, addressConsumerHome.getStreet());
            statement.setLong(3, addressConsumerHome.getId());
            statement.executeUpdate();

            return findById(addressConsumerHome.getId());
        }
    }
}
