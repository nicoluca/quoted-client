package org.nico.quoted.dao;

import org.nico.quoted.domain.Identifiable;

@FunctionalInterface
public interface Update<T extends Identifiable> {
    T update(T t);
}
