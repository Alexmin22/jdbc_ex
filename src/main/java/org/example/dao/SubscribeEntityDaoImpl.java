package org.example.dao;

import org.example.entity.Company;
import org.example.entity.Subscribe;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubscribeEntityDaoImpl implements EntityDao<Subscribe> {
    private static final SubscribeEntityDaoImpl INSTANCE = new SubscribeEntityDaoImpl();
    public static SubscribeEntityDaoImpl getInstance() {
        return INSTANCE;
    }
    private static final  String SAVE = "INSERT INTO Subscribe (name) VALUES (?)";
    private static final  String UPDATE = "UPDATE Subscribe SET name = ? WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, name FROM Subscribe";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Subscribe save(Subscribe subscribe) {
        try (Connection connection = ConnectionManager.get();
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
    public Subscribe update(Subscribe subscribe) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, subscribe.getName());
            statement.setLong(2, subscribe.getId());
            statement.executeUpdate();

            return findById(subscribe.getId());
        }
    }

    private Subscribe addSubscribe(ResultSet rs) throws SQLException {
        return new Subscribe(rs.getLong("id"),
                rs.getString("name"));
    }

    @Override
    public Subscribe findById(long id) throws SQLException {
        Subscribe subscribe = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    subscribe = addSubscribe(rs);
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
    public List<Subscribe> findAll() throws SQLException {
        List<Subscribe> sub = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Subscribe subscribe = addSubscribe(rs);
                sub.add(subscribe);
            }
        }
        return sub;
    }
}
