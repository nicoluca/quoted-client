package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.nico.quoted.http.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ReadAllDao<T> extends Dao<T> implements ReadAll<T> {

    public ReadAllDao(Class<T> type, String url) {
        super(type, url);
    }

    public ReadAllDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    public List<T> readAll() {
        Optional<String> jsonResult = httpService.get(url);

        if (jsonResult.isEmpty())
            return List.of();

        try {
            CollectionType listType =
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return objectMapper.readValue(jsonResult.get(), listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
