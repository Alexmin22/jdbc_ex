package org.example.service.hibernate;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.dao.hibernateRepository.Repository;
import org.example.dto.hibernate.AddressCreateDto;
import org.example.dto.hibernate.AddressReadDto;
import org.example.entity.AddressConsumerHome;
import org.example.mapper.AddressCreateEditMapper;
import org.example.mapper.AddressReadMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AddressService implements Service<Long, AddressReadDto, AddressCreateDto> {


    private final AddressReadMapper readMapper;
    private final AddressCreateEditMapper createEditMapper;
    private final Repository<Long, AddressConsumerHome> repository;
    @Override
    @Transactional
    public AddressReadDto save(AddressCreateDto dto) {
        return Optional.of(dto)
                .map(createEditMapper::map)
                .map(repository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(address -> {
                    repository.delete(address.getId());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<AddressReadDto> update(Long id, AddressReadDto dto) {
        return repository.findById(id)
                .map(address -> readMapper.map(dto, address))
                .map(repository::save)
                .map(readMapper::map);
    }

    @Override
    public Optional<AddressReadDto> findById(Long id, Map<String, Object> properties) {
        return repository.findById(id)
                .map(readMapper::map);
    }

    @Override
    public List<AddressReadDto> findAll() {
        return repository.findAll().stream().map(readMapper::map).toList();
    }
}
