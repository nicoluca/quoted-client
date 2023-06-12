package org.nico.quoted.service;

import org.nico.quoted.domain.Identifiable;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends Identifiable> {
    T create(T t); // Change to return T?
    List<T> readAll();
    T update(T t); // Change to return T?
    void delete(T t);
}
