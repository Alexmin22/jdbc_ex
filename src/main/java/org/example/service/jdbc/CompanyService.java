package org.example.service.jdbc;

import org.example.dao.jdbc.CompanyEntityDaoImpl;
import org.example.entity.Company;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CompanyService {

    private static final CompanyEntityDaoImpl COMPANY_ENTITY_DAO = new CompanyEntityDaoImpl();

    public Company add(Company com, Connection c) {
        return COMPANY_ENTITY_DAO.save(com, c);
    }

    Company update(Company com, Connection connection) {
        try {
            return COMPANY_ENTITY_DAO.update(com, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Company> getAll(Connection connection) {
        try {
            return COMPANY_ENTITY_DAO.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Company getById(Long id, Connection connection) {
        try {
            return COMPANY_ENTITY_DAO.findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean deleteById(Long id, Connection connection) {
        try {
            return COMPANY_ENTITY_DAO.deleteById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
