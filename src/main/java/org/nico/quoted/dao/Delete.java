package org.nico.quoted.dao;

import org.nico.quoted.domain.Identifiable;

public interface Delete<T extends Identifiable> {
    void delete(T t);
}
