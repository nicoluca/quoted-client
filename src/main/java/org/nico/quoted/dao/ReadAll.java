package org.nico.quoted.dao;


import java.util.List;

@FunctionalInterface
public interface ReadAll<T> {
    List<T> readAll();
}
