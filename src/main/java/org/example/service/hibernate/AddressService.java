package org.example.service.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.example.dao.hibernateRepository.AdressRepository;
import org.example.entity.AddressConsumerHome;

import java.util.List;
import java.util.Optional;

public class AddressService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager em = emf.createEntityManager();

    private final AdressRepository adressRepository = new AdressRepository(em);

    public AddressConsumerHome add(AddressConsumerHome address) {
        return adressRepository.save(address);
    }

    public void deleteByID(Long id) {
        adressRepository.delete(id);
    }

    public void update(AddressConsumerHome address) {
        adressRepository.update(address);
    }

    public Optional<AddressConsumerHome> getByID(Long id) {
        return adressRepository.findById(id);
    }

    public List<AddressConsumerHome> getAll() {
        return adressRepository.findAll();
    }
}
