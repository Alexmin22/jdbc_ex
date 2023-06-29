package org.example.service.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.AllArgsConstructor;
import org.example.dao.hibernateRepository.ConsumerRepository;
import org.example.dao.hibernateRepository.Repository;
import org.example.dto.hibernate.ConsumerCreateEditDto;
import org.example.dto.hibernate.ConsumerReadDto;
import org.example.entity.Company;
import org.example.entity.Consumer;
import org.example.mapper.ConsumerCreateEditMapper;
import org.example.mapper.ConsumerReadMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@AllArgsConstructor
public class ConsumerService implements Service<Long, ConsumerReadDto, ConsumerCreateEditDto> {

    private final ConsumerReadMapper consumerReadMapper;
    private final ConsumerCreateEditMapper createEditMapper;
    private final Repository<Long, Consumer> repository;
    @Override
    public ConsumerReadDto save(ConsumerCreateEditDto dto) {
        return Optional.of(dto)
                .map(createEditMapper::map)
                .map(repository::save)
                .map(consumerReadMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(consumer -> {
                    delete(consumer.getId());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<ConsumerReadDto> update(Long id, ConsumerReadDto dto) {
        return repository.findById(id)
                .map(cons -> consumerReadMapper.map(dto, cons))
                .map(repository::save)
                .map(consumerReadMapper::map);
    }

    @Override
    public Optional<ConsumerReadDto> findById(Long id, Map<String, Object> properties) {
        return repository.findById(id)
                .map(consumerReadMapper::map);
    }

    @Override
    public List<ConsumerReadDto> findAll() {
        return repository.findAll().stream().map(consumerReadMapper::map).toList();
    }
}
