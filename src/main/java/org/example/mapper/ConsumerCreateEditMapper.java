package org.example.mapper;

import org.example.dto.hibernate.AddressCreateDto;
import org.example.dto.hibernate.CompanyCreateEditDto;
import org.example.dto.hibernate.ConsumerCreateEditDto;
import org.example.entity.AddressConsumerHome;
import org.example.entity.Company;
import org.example.entity.Consumer;

public class ConsumerCreateEditMapper implements Mapper<ConsumerCreateEditDto, Consumer> {

    private CompanyCreateEditMapper comMapper;
    private AddressCreateEditMapper addressMapper;

    private AddressConsumerHome getAddress(ConsumerCreateEditDto cons) {
        AddressCreateDto addressCreateDto = cons.getAddressCreateDto();
        return addressMapper.map(addressCreateDto);
    }

    private Company getCompany(ConsumerCreateEditDto cons) {
        CompanyCreateEditDto companyCreateDto = cons.getCompanyCreateDto();
        return comMapper.map(companyCreateDto);
    }
    @Override
    public Consumer map(ConsumerCreateEditDto object) {

        Consumer consumer = new Consumer();
        consumer.setName(object.getName());
        consumer.setEmail(object.getEmail());
        consumer.setRole(object.getRole());
        consumer.setCompany(getCompany(object));
        consumer.setAddress(getAddress(object));

        return consumer;
    }

    @Override
    public Consumer map(ConsumerCreateEditDto fromObject, Consumer toObject) {
        toObject.setName(fromObject.getName());
        toObject.setEmail(fromObject.getEmail());
        toObject.setRole(fromObject.getRole());
        toObject.setAddress(getAddress(fromObject));
        toObject.setCompany(getCompany(fromObject));

        return toObject;
    }
}
