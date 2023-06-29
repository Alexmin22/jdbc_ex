package org.example.mapper;

import org.example.dto.hibernate.AddressReadDto;
import org.example.dto.hibernate.CompanyReadDto;
import org.example.dto.hibernate.ConsumerReadDto;
import org.example.entity.AddressConsumerHome;
import org.example.entity.Company;
import org.example.entity.Consumer;

public class ConsumerReadMapper implements Mapper<ConsumerReadDto, Consumer> {

    private CompanyReadMapper companyReadMapper;
    private AddressReadMapper addressReadMapper;
    @Override
    public Consumer map(ConsumerReadDto object) {

        AddressConsumerHome addressConsumerHome = addressReadMapper.map(object.getAddress());
        Company company = companyReadMapper.map(object.getCompanyReadDto());
        Consumer consumer = new Consumer();

        consumer.setId(object.getId());
        consumer.setName(object.getName());
        consumer.setEmail(object.getEmail());
        consumer.setRole(object.getRole());
        consumer.setAddress(addressConsumerHome);
        consumer.setCompany(company);

        return consumer;
    }

    public ConsumerReadDto map(Consumer consumer) {

        AddressReadDto addressReadDto = addressReadMapper.map(consumer.getAddress());
        CompanyReadDto companyReadDto = companyReadMapper.map(consumer.getCompany());

        return new ConsumerReadDto(consumer.getId(),
                consumer.getName(),
                consumer.getEmail(),
                consumer.getRole(),
                companyReadDto,
                addressReadDto);
    }
}
