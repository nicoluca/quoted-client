package org.nico.quoted.dao;

import java.util.Optional;

public interface Read<T> {
    Optional<T> read(String id);
}
