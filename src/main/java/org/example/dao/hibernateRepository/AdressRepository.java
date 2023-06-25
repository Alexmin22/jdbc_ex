package org.example.dao.hibernateRepository;

import jakarta.persistence.EntityManager;
import org.example.entity.AddressConsumerHome;


public class CompanyRepository extends RepositoryBase<Long, AddressConsumerHome> {
    public AdressRepository(EntityManager entityManager) {
        super(AddressConsumerHome.class, entityManager);
    }
}
