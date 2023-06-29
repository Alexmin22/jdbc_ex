package org.example.mapper;

import org.example.dto.hibernate.AddressCreateDto;
import org.example.entity.AddressConsumerHome;

public class AddressCreateEditMapper implements Mapper<AddressCreateDto, AddressConsumerHome> {
    @Override
    public AddressConsumerHome map(AddressCreateDto object, AddressConsumerHome addressConsumerHome) {
        addressConsumerHome.setCity(object.getCity());
        addressConsumerHome.setStreet(object.getStreet());

        return addressConsumerHome;
    }

    @Override
    public AddressConsumerHome map(AddressCreateDto dto) {
        AddressConsumerHome address = new AddressConsumerHome();
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        return address;
    }
}
