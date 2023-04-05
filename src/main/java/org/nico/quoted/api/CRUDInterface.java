package org.nico.quoted.api;

public interface CRUDInterface<T> {
    T add(T t);
    void delete(T t);
    void update(T t);

}
