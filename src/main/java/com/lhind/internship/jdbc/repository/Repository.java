package com.lhind.internship.jdbc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ENTITY, ID> {

    List<ENTITY> findAll();

    Optional<ENTITY> findById(final ID id);

    boolean exists(final ID id);

    ENTITY save(final ENTITY entity);

    void delete(final ID id);
}
