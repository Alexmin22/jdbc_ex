package org.example.dto.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entity.Role;

@Data
@AllArgsConstructor
public class ConsumerReadDto {

    private Long id;

    private String name;

    private String email;

    private Role role;

    private CompanyReadDto companyReadDto;

    private AddressReadDto address;
}
