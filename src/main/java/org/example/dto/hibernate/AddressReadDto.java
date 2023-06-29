package org.example.dto.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressReadDto {

    private Long id;
    private String city;
    private String street;
}
