package org.example;

import org.example.dao.hibernateRepository.CompanyRepository;
import org.example.dao.hibernateRepository.Repository;
import org.example.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateStarter {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Company.class);
        configuration.configure();

        try (var sessionFactory = configuration.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                Repository repository = new CompanyRepository(session);

                final var google = Company.builder()
                        .name("microsoft3")
                        .build();

                session.persist(google);

                session.flush();

                System.out.println(repository.findAll());

                session.getTransaction().commit();
            }

            try (var session = sessionFactory.openSession()) {

            }
        }
    }
}
