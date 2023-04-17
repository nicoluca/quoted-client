package org.nico.quoted.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T> {
    void create(T t);
    Optional<T> readById(long id);
    List<T> readAll();
    void update(T t);
    void delete(T t);
}
