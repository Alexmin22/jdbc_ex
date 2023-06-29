package org.example.mapper;

import org.example.dto.hibernate.AddressReadDto;
import org.example.entity.AddressConsumerHome;

public class AddressReadMapper implements Mapper<AddressReadDto, AddressConsumerHome> {
    @Override
    public AddressConsumerHome map(AddressReadDto object) {
        return new AddressConsumerHome(object.getId(), object.getCity(), object.getStreet());
    }

    public AddressReadDto map(AddressConsumerHome address) {
        return new AddressReadDto(address.getId(), address.getCity(), address.getStreet());
    }
}
