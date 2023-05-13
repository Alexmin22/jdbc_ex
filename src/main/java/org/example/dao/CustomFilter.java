package org.example.dao;

public record CustomFilter(
        int limit,
        int offset,
        String name,
        String email
) {}