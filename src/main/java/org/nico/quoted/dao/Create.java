package org.nico.quoted.dao;

@FunctionalInterface
public interface Create<T> {
    T create(T t);
}
