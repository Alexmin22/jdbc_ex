package org.example.dao.jdbc;

import org.example.dao.jdbc.CompanyEntityDaoImpl;
import org.example.entity.Company;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyEntityDaoImplTest {

    private static final CompanyEntityDaoImpl companyEntityDao = new CompanyEntityDaoImpl();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }

    @Tag("login")
    @Test
    void findAllTest() {
        try {
            List<Company> list = companyEntityDao.findAll(ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(2, list.size()),
                    () -> assertEquals(new Company(1L, "Magnit"), list.get(0)),
                    () -> assertEquals(new Company(2L, "RostselMash"), list.get(1))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Tag("login")
    @Test
    void findByIDTest() {
        try {
            Company company = companyEntityDao.findById(1L, ConnectionManagerTest.buildConnection());

            assertThat(company)
                    .isEqualTo(new Company(1L, "Magnit"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Tag("login")
    @Test
    void saveTest() {
        Company company = new Company(3L, "Romashka");

        Company com2 = companyEntityDao.save(company, ConnectionManagerTest.buildConnection());

        assertThat(com2).isEqualTo(company);
    }

    @Tag("login")
    @Test
    void updateTest() {
        try {
            Company company = companyEntityDao.findById(1L, ConnectionManagerTest.buildConnection());
            company.setName("Romashka plus");
            Company company1 =
                    companyEntityDao.update(company, ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(company1.getId(), 1L),
                    () -> assertEquals(company1.getName(), "Romashka plus"),
                    () -> assertEquals(company1, company)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
