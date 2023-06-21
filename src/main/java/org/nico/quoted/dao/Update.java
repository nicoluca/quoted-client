package org.nico.quoted.dao;

import org.nico.quoted.domain.Identifiable;

public interface Update<T extends Identifiable> {
    T update(T t);
}
