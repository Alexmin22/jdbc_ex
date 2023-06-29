package org.example.dto.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entity.Role;

@Data
@AllArgsConstructor
public class ConsumerCreateEditDto {

    private String name;
    private String email;
    private Role role;
    private CompanyCreateEditDto companyCreateDto;
    private AddressCreateDto addressCreateDto;
}
