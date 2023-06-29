package org.example.dto.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AddressCreateDto {

    private String city;
    private String street;
}
