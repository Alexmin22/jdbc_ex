package org.example.dao.hibernateRepository;

import jakarta.persistence.EntityManager;
import org.example.entity.Company;


public class CompanyRepository extends RepositoryBase<Long, Company> {
    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }
}
