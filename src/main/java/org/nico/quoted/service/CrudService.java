package org.nico.quoted.service;

import org.nico.quoted.domain.Identifiable;

import java.util.List;

public interface CrudService<T extends Identifiable> {

    // Only the quote service implements all methods.
    // The other services have a differing set of methods.

    T create(T t);
    List<T> readAll();
    T update(T t);
    void delete(T t);
}
