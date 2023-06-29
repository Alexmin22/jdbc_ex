package org.example.service.hibernate;

import lombok.Cleanup;
import org.example.dao.hibernateRepository.CompanyRepository;
import org.example.dto.hibernate.CompanyCreateEditDto;
import org.example.entity.Company;
import org.example.mapper.CompanyCreateEditMapper;
import org.example.mapper.CompanyReadMapper;
import org.example.service.hibernate.CompanyService;
import org.example.util.hibernate.HibernateUtilTest;
import org.junit.jupiter.api.Test;

class CompanyServiceTest {
    // first level cache

    @Test
    void test() {
        @Cleanup final var sessionFactory = HibernateUtilTest.buildSessionFactory();
        @Cleanup final var session = sessionFactory.openSession();

        session.beginTransaction();
        CompanyRepository repository = new CompanyRepository(session);
        CompanyService service = new CompanyService(new CompanyReadMapper(), new CompanyCreateEditMapper(), repository);

        session.createNativeQuery("CREATE TABLE company(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30))", Company.class).executeUpdate();

        service.save(new CompanyCreateEditDto("Max"));

        session.getTransaction().commit();
        System.out.println(service.findById(1L));

    }
}
