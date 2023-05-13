package org.example.dao;

import org.example.entity.Consumer;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ConsDaoImpl implements ConsDao {

    private static final ConsDaoImpl INSTANCE = new ConsDaoImpl();

    private ConsDaoImpl() {}

    public static ConsDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final  String SAVE = "INSERT INTO Consumer (email, name) VALUES (?, ?)";
    private static final  String DELETE = "DELETE FROM Consumer WHERE id =?";
    private static final  String UPDATE = "UPDATE Consumer SET email = ?, name = ? WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, email, name FROM Consumer";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Consumer save(Consumer consumer) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, consumer.getEmail());
            statement.setString(2, consumer.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                consumer.setId(generatedKeys.getInt(1));
            }
            return consumer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;   //executeUpdate() вернет инт, мы переводим этот инт в тру фолс
        }
    }

    @Override
    public Consumer update(Consumer consumer) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, consumer.getEmail());
            statement.setString(2, consumer.getName());
            statement.setInt(3, consumer.getId());
            statement.executeUpdate();

            return findConsumerById(consumer.getId());
        }
    }

    private Consumer addConsumer(ResultSet rs) throws SQLException {
        return new Consumer(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
    }

    @Override
    public Consumer findConsumerById(int id) throws SQLException {
        Consumer consumer = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    consumer = addConsumer(rs);
                } else {
                    throw new CustomException("Consumer with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
        return consumer;
    }

    @Override
    public List<Consumer> findAllConsumer() throws SQLException {
        List<Consumer> consumers = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Consumer consumer = addConsumer(rs);
                consumers.add(consumer);
            }
        }
        return consumers;
    }

    @Override
    public List<Consumer> findAllConsumer(CustomFilter customFilter) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        List<String> filterWhere = new ArrayList<>();

        if (customFilter.name()!= null) {
            parameters.add("%" + customFilter.name() + "%");
            filterWhere.add("name LIKE ?");
        }
        if (customFilter.email()!= null) {
            parameters.add("%" + customFilter.email() + "%");
            filterWhere.add("email LIKE ?");
        }

        String where = "";
        parameters.add(customFilter.limit());
        parameters.add(customFilter.offset());

        if (filterWhere != null) {
            where = filterWhere.stream()
                    .collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        } else {
            where = filterWhere.stream()
                  .collect(Collectors.joining(" AND ", "", "LIMIT ? OFFSET ? "));
        }

        String sql = FIND_ALL + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();
            List<Consumer> consumers = new ArrayList<>(customFilter.limit());
            while (rs.next()) {
                Consumer consumer = addConsumer(rs);
                consumers.add(consumer);
            }
            return consumers;
        }
    }
}
