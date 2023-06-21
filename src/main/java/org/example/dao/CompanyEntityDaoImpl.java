package org.example.dao;

import org.example.entity.Company;
import org.example.entity.Consumer;
import org.example.utils.ConnectionManager;
import org.example.utils.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyEntityDaoImpl implements EntityDao<Company> {

    private static final CompanyEntityDaoImpl INSTANCE = new CompanyEntityDaoImpl();
    public static CompanyEntityDaoImpl getInstance() {
        return INSTANCE;
    }
    private static final  String SAVE = "INSERT INTO Company (name) VALUES (?)";
    private static final  String UPDATE = "UPDATE Company SET name = ? WHERE id =?";
    private static final  String FIND_ALL = "SELECT id, name FROM Company";
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

    private Company addCompany(ResultSet rs) throws SQLException {
        return new Company(rs.getInt("id"),
                rs.getString("name"));
    }

    @Override
    public Company findById(long id, Connection connection) throws SQLException {
        Company company = null;
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    company = addCompany(rs);
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
                Company company = addCompany(rs);
                companies.add(company);
            }
        }
        return companies;
    }

}
