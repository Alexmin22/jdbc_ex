package org.example.dao;

import org.example.entity.Subscribe;
import org.example.utils.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribeEntityDaoImpl implements EntityDao<Long, Subscribe> {
    private static final SubscribeEntityDaoImpl INSTANCE = new SubscribeEntityDaoImpl();
    public static SubscribeEntityDaoImpl getInstance() {
        return INSTANCE;
    }
    private static final  String SAVE = "INSERT INTO subscribe (name) VALUES (?)";
    private static final  String UPDATE = "UPDATE subscribe SET name = ? WHERE id =?";
    private static final String DELETE = "DELETE FROM subscribe WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, name FROM subscribe";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Subscribe save(Subscribe subscribe, Connection connection) {
        try (connection;
             PreparedStatement statement =
                     connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, subscribe.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                subscribe.setId(generatedKeys.getLong(1));
            }
            return subscribe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subscribe update(Subscribe subscribe, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, subscribe.getName());
            statement.setLong(2, subscribe.getId());
            statement.executeUpdate();

            return findById(subscribe.getId(), connection);
        }
    }

    @Override
    public Subscribe findById(Long id, Connection connection) throws SQLException {
        Subscribe subscribe = null;
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    subscribe = new Subscribe(rs.getLong("id"),
                            rs.getString("name"));
                } else {
                    throw new CustomException("Company with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
        return subscribe;
    }

    @Override
    public List<Subscribe> findAll(Connection connection) throws SQLException {
        List<Subscribe> sub = new ArrayList<>();

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Subscribe subscribe = new Subscribe(rs.getLong("id"),
                        rs.getString("name"));
                sub.add(subscribe);
            }
        }
        return sub;
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
