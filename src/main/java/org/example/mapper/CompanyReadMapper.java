package org.example.mapper;

import org.example.dto.hibernate.CompanyReadDto;
import org.example.entity.Company;

public class CompanyReadMapper implements Mapper<CompanyReadDto, Company> {

    @Override
    public Company map(CompanyReadDto object) {
        return new Company(
                object.id(),
                object.name()
        );
    }

    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
