package org.example.service.hibernate;

import org.example.dto.hibernate.CompanyReadDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Service<K, R, C> {

    R save(C dto);

    boolean delete(K id);

    Optional<R> update(K id, R dto);

    default Optional<R> findById(K id) {
        return findById(id, emptyMap());
    }

    Optional<R> findById(K id, Map<String, Object> properties);

    List<R> findAll();
}
