package org.example.service.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.example.dao.hibernateRepository.ConsumerRepository;
import org.example.entity.Company;
import org.example.entity.Consumer;

import java.util.List;
import java.util.Optional;

public class ConsumerService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager em = emf.createEntityManager();

    private final ConsumerRepository consumerRepository = new ConsumerRepository(em);

    public Consumer add(Consumer cons) {
        return consumerRepository.save(cons);
    }

    public void deleteByID(Long id) {
        consumerRepository.delete(id);
    }

    public void update(Consumer consumer) {
        consumerRepository.update(consumer);
    }

    public Optional<Consumer> getByID(Long id) {
        return consumerRepository.findById(id);
    }

    public List<Consumer> getAll() {
        return consumerRepository.findAll();
    }
}
