package org.example.dao;

import org.example.entity.Company;
import org.example.utils.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyEntityDaoImpl implements EntityDao<Long, Company> {

    private static final CompanyEntityDaoImpl INSTANCE = new CompanyEntityDaoImpl();
    public static CompanyEntityDaoImpl getInstance() {
        return INSTANCE;
    }
    private static final  String SAVE = "INSERT INTO company (name) VALUES (?)";
    private static final  String UPDATE = "UPDATE company SET name = ? WHERE id =?";
    private static final String DELETE = "DELETE FROM company WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, name FROM company";
    private static final  String FIND_BY_ID = FIND_ALL + " WHERE id =?";

    @Override
    public Company save(Company company, Connection connection) {
        try (connection;
             PreparedStatement statement =
                     connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {    //ключи возвращают все поля объекта
            statement.setString(1, company.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                company.setId(generatedKeys.getLong(1));
            }
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company update(Company company, Connection connection) throws SQLException {
        try (connection;
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, company.getName());
            statement.setLong(2, company.getId());
            statement.executeUpdate();

            return findById(company.getId(), connection);
        }
    }

    @Override
    public Company findById(Long id, Connection connection) throws SQLException {
        Company company = null;
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    company = new Company(rs.getInt("id"),
                            rs.getString("name"));
                } else {
                    throw new CustomException("Company with id=" + id + " not found");
                }
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
        return company;
    }

    @Override
    public List<Company> findAll(Connection connection) throws SQLException {
        List<Company> companies = new ArrayList<>();

        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Company company = new Company(rs.getInt("id"),
                        rs.getString("name"));
                companies.add(company);
            }
        }
        return companies;
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
