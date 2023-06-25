package org.example.service.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.example.dao.hibernateRepository.CompanyRepository;
import org.example.entity.Company;

import java.util.List;
import java.util.Optional;

public class CompanyService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager em = emf.createEntityManager();

    private final CompanyRepository companyRepository = new CompanyRepository(em);

    public Company add(Company company) {
        return companyRepository.save(company);
    }

    public void deleteByID(Long id) {
        companyRepository.delete(id);
    }

    public void update(Company company) {
        companyRepository.update(company);
    }

    public Optional<Company> getByID(Long id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }
}
