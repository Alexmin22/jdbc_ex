package org.example.dao.jdbc;

import org.example.entity.AddressConsumerHome;
import org.example.entity.Company;
import org.example.entity.Consumer;
import org.example.entity.Role;
import org.example.utils.jdbc.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumerEntityDaoImpl implements EntityDao<Long, Consumer> {

    private static final CompanyEntityDaoImpl INSTANCE = new CompanyEntityDaoImpl();
    private static final AddressConsumerHomeEntityDaoImpl INSTANCE2 = new AddressConsumerHomeEntityDaoImpl();
    private static final String SAVE = "INSERT INTO consumer (email, name, roles_id, address_id, company_id)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE consumer SET email = ?, name = ?, roles_id = ?," +
            " address_id = ?, company_id = ? WHERE id =?";
    private static final String DELETE = "DELETE FROM consumer WHERE id =?";
    private static final String FIND_ALL = "SELECT id, email, name, roles_id, address_id, company_id FROM consumer";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Consumer save(Consumer consumer, Connection connection) {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, consumer.getEmail());
            statement.setString(2, consumer.getName());
            statement.setInt(3, consumer.getRole().ordinal() + 1);
            statement.setLong(4, consumer.getAddress().getId());
            statement.setLong(5, consumer.getCompany().getId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                consumer.setId(generatedKeys.getLong(1));
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
            statement.setInt(3, consumer.getRole().ordinal() + 1);
            statement.setLong(4, consumer.getAddress().getId());
            statement.setLong(5, consumer.getCompany().getId());
            statement.setLong(6, consumer.getId());
            statement.executeUpdate();

            return findById(consumer.getId(), connection);
        }
    }

    @Override
    public Consumer findById(Long id, Connection connection) {
        Consumer consumer = null;

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                long com_id;
                long adr_id;
                if (rs.next()) {
                    consumer = new Consumer(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("roles_id") == 1 ? Role.ADMIN : Role.USER,
                            null,
                            null);
                    com_id = rs.getLong("company_id");
                    adr_id = rs.getLong("address_id");
                } else {
                    throw new CustomException("Consumer with id=" + id + " not found");
                }

                AddressConsumerHome adres = INSTANCE2.findByIdNotClosed(adr_id, connection);
                Company byId = INSTANCE.findByIdNotClosed(com_id, connection);
                consumer.setCompany(byId);
                consumer.setAddress(adres);

            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consumer;
    }

    @Override
    public List<Consumer> findAll(Connection connection) throws SQLException {
        List<Consumer> consumers = new ArrayList<>();

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            long com_id;
            long adr_id;
            while (rs.next()) {
                com_id = rs.getLong("company_id");
                adr_id = rs.getLong("address_id");

                AddressConsumerHome adres = INSTANCE2.findByIdNotClosed(adr_id, connection);
                Company byId = INSTANCE.findByIdNotClosed(com_id, connection);

                Consumer consumer = new Consumer(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("roles_id") == 1 ? Role.ADMIN : Role.USER,
                        byId,
                        adres);

                consumers.add(consumer);
            }
        }
        return consumers;
    }

    public List<Consumer> findAllWithFilter(CustomFilter customFilter, Connection connection) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        List<String> filterWhere = new ArrayList<>();

        if (customFilter.name() != null) {
            parameters.add("%" + customFilter.name() + "%");
            filterWhere.add("name LIKE ?");
        }
        if (customFilter.email() != null) {
            parameters.add("%" + customFilter.email() + "%");
            filterWhere.add("email LIKE ?");
        }
        if (customFilter.role() != null) {
            parameters.add(customFilter.role().ordinal() + 1);
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
        List<Consumer> consumers = new ArrayList<>(customFilter.limit());

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();
            long com_id;
            long adr_id;

            while (rs.next()) {
                com_id = rs.getLong("company_id");
                adr_id = rs.getLong("address_id");

                AddressConsumerHome adres = INSTANCE2.findByIdNotClosed(adr_id, connection);
                Company byId = INSTANCE.findByIdNotClosed(com_id, connection);

                Consumer consumer = new Consumer(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("roles_id") == 1 ? Role.ADMIN : Role.USER,
                        byId,
                        adres);

                consumers.add(consumer);
            }
        }
        return consumers;
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
