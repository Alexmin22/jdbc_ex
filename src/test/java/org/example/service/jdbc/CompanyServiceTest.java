package org.example.service.jdbc;

import org.example.entity.Company;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyServiceTest {

    private static final CompanyService companyService = new CompanyService();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }

    @Test
    void getAllTest() {
        List<Company> list = companyService.getAll(ConnectionManagerTest.buildConnection());

        assertAll(
                () -> assertEquals(2, list.size()),
                () -> assertEquals(new Company(1L, "Magnit"), list.get(0)),
                () -> assertEquals(new Company(2L, "RostselMash"), list.get(1))
        );
    }

    @Test
    void getByIDTest() {
        Company company = companyService.getById(1L, ConnectionManagerTest.buildConnection());

        assertThat(company)
                .isEqualTo(new Company(1L, "Magnit"));
    }

    @Test
    void addTest() {
        Company company = new Company(3L, "Romashka");

        Company com2 = companyService.add(company, ConnectionManagerTest.buildConnection());

        assertThat(com2).isEqualTo(company);
    }

    @Test
    void updateTest() {
        Company company = companyService.getById(1L, ConnectionManagerTest.buildConnection());
        company.setName("Romashka plus");
        Company company1 =
                companyService.update(company, ConnectionManagerTest.buildConnection());

        assertAll(
                () -> assertEquals(company1.getId(), 1L),
                () -> assertEquals(company1.getName(), "Romashka plus"),
                () -> assertEquals(company1, company)
        );
    }
}
