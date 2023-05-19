package org.nico.quoted.repository;

import org.nico.quoted.domain.Identifiable;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends Identifiable> {
    T create(T t); // Change to return T?
    Optional<T> readById(long id); // Only used in tests
    List<T> readAll();
    T update(T t); // Change to return T?
    void delete(T t);
}
