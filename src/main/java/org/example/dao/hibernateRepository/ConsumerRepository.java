package org.example.dao.hibernateRepository;

import jakarta.persistence.EntityManager;
import org.example.entity.Consumer;

public class ConsumerRepository extends RepositoryBase<Long, Consumer> {
    public ConsumerRepository(EntityManager entityManager) {
        super(Consumer.class, entityManager);
    }
}
