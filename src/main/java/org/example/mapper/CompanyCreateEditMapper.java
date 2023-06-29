package org.example.mapper;

import org.example.dto.hibernate.CompanyCreateEditDto;
import org.example.entity.Company;

public class CompanyCreateEditMapper implements Mapper<CompanyCreateEditDto, Company> {

    @Override
    public Company map(CompanyCreateEditDto fromObject, Company toObject) {
        toObject.setName(fromObject.name());
        return toObject;
    }

    @Override
    public Company map(CompanyCreateEditDto object) {
        final var company = new Company();
        company.setName(object.name());
        return company;
    }
}
