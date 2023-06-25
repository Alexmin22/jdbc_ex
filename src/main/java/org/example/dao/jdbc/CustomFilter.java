package org.example.dao.jdbc;

import org.example.entity.Role;

public record CustomFilter(
        int limit,
        int offset,
        String name,
        String email,
        Role role
) {}