package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.http.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
public class ReadAllDao<T> extends Dao<T> implements ReadAll<T> {

    public ReadAllDao(Class<T> type, String url) {
        super(type, url);
    }

    // Used for unit testing, to inject a mock HttpService
    public ReadAllDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    public List<T> readAll() {
        log.info("Getting all {} from {}", type, url);
        Optional<String> jsonResult = httpService.get(url);
        log.info("Response: {}", jsonResult);

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
