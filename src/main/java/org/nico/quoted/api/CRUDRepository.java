package org.nico.quoted.api;

import java.util.List;

public interface CRUDRepository<T> {
    void create(T t);
    T readById(long id);
    List<T> readAll();
    void update(T t);
    void delete(T t);
}
