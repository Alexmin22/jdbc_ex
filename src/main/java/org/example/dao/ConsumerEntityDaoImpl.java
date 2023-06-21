package org.example.dao;

import org.example.entity.AddressConsumerHome;
import org.example.entity.Consumer;
import org.example.entity.Role;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ConsumerEntityDaoImpl implements EntityDao<Consumer> {

    private static final ConsumerEntityDaoImpl INSTANCE = new ConsumerEntityDaoImpl();

    private ConsumerEntityDaoImpl() {}

    public static ConsumerEntityDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final  String SAVE = "INSERT INTO Consumer (email, name, roles_id, address_id, company_id)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final  String UPDATE = "UPDATE Consumer SET email = ?, name = ?, roles_id = ?," +
            " address_id = ?, company_id = ? WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, email, name, roles_id, address_id, company_id FROM Consumer";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Consumer save(Consumer consumer, Connection connection) {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, consumer.getEmail());
            statement.setString(2, consumer.getName());
            statement.setInt(3, consumer.getRole().ordinal()+1);
            statement.setLong(4, consumer.getAddress().getId());
            statement.setLong(5, consumer.getCompany().getId());
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
    public Consumer update(Consumer consumer, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, consumer.getEmail());
            statement.setString(2, consumer.getName());
            statement.setInt(3, consumer.getRole().ordinal()+1);
            statement.setLong(4, consumer.getAddress().getId());
            statement.setLong(5, consumer.getCompany().getId());
            statement.setLong(6, consumer.getId());
            statement.executeUpdate();

            return findById(consumer.getId(), connection);
        }
    }

    private Consumer addConsumer(ResultSet rs) throws SQLException {
        return new Consumer(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("roles_id") == 1 ? Role.ADMIN : Role.USER,
                CompanyEntityDaoImpl.getInstance().findById(
                rs.getLong("company_id"), ConnectionManager.get()),
                AddressConsumerHomeEntityDaoImpl.getInstance()
                .findById(rs.getLong("address_id"), ConnectionManager.get()));
    }

    @Override
    public Consumer findById(long id, Connection connection) throws SQLException {
        Consumer consumer = null;
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
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
    public List<Consumer> findAll(Connection connection) throws SQLException {
        List<Consumer> consumers = new ArrayList<>();

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Consumer consumer = addConsumer(rs);
                consumers.add(consumer);
            }
        }
        return consumers;
    }

    public List<Consumer> findAllWithFilter(CustomFilter customFilter, Connection connection) throws SQLException {
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
        if (customFilter.role()!= null) {
            parameters.add(customFilter.role().ordinal()+1);
            filterWhere.add("roles_id = ?");
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
        try (connection;
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
