package org.example.service.hibernate;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.dao.hibernateRepository.Repository;
import org.example.dto.hibernate.CompanyCreateEditDto;
import org.example.dto.hibernate.CompanyReadDto;
import org.example.entity.Company;
import org.example.mapper.CompanyCreateEditMapper;
import org.example.mapper.CompanyReadMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CompanyService implements Service<Long, CompanyReadDto, CompanyCreateEditDto> {

    private final CompanyReadMapper companyReadMapper;

    private final CompanyCreateEditMapper companyCreateEditMapper;

    private final Repository<Long, Company> repository;

    @Override
    @Transactional
    public CompanyReadDto save(CompanyCreateEditDto dto) {
        return Optional.of(dto)
                .map(companyCreateEditMapper::map)
                .map(repository::save)
                .map(companyReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity.getId());
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public Optional<CompanyReadDto> update(Long id, CompanyReadDto dto) {
        return repository.findById(id)
                .map(entity -> companyReadMapper.map(dto, entity))
                .map(repository::save)
                .map(companyReadMapper::map);
    }

    @Transactional
    @Override
    public Optional<CompanyReadDto> findById(Long id, Map<String, Object> properties) {
        return repository.findById(id).map(companyReadMapper::map);
    }

    @Transactional
    @Override
    public List<CompanyReadDto> findAll() {
        return repository.findAll().stream().map(companyReadMapper::map).toList();
    }
}
