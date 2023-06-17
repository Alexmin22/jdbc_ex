package org.example.dao;

import org.example.entity.Role;

public record CustomFilter(
        int limit,
        int offset,
        String name,
        String email,
        Role role
) {}