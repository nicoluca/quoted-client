package org.nico.quoted.serialization;

import com.google.gson.Gson;

import java.util.List;

@FunctionalInterface
public interface ListDeserializer<T> {
    List<T> deserializeList(String json, Gson gson);
}
