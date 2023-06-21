package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nico.quoted.http.HttpService;

import java.util.Optional;

public class ReadDao<T> extends Dao<T> implements Read<T> {
    public ReadDao(Class<T> type, String url) {
        super(type, url);
    }

    public ReadDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    // TODO - test

    @Override
    public Optional<T> read(String id) {
        Optional<String> jsonResult = httpService.get(url + "/" + id);

        if (jsonResult.isEmpty())
            return Optional.empty();

        try {
            return Optional.of(objectMapper.readValue(jsonResult.get(), type));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
