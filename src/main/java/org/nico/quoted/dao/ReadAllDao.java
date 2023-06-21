package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.nico.quoted.config.Config;
import org.nico.quoted.http.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ReadAllDao<T> {

    private final HttpService httpService;
    private final String url;
    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    public ReadAllDao(String url, Class<T> clazz) {
        this.url = url;
        this.clazz = clazz;
        this.httpService = Config.HTTP_SERVICE;
        this.objectMapper = new ObjectMapper();
        objectMapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public ReadAllDao(String url, HttpService httpService, Class<T> clazz) {
        this.url = url;
        this.clazz = clazz;
        this.httpService = httpService;
        this.objectMapper = new ObjectMapper();
        objectMapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public List<T> readAll() {
        Optional<String> jsonResult = httpService.get(url);

        if (jsonResult.isEmpty())
            return List.of();

        try {
            CollectionType listType =
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(jsonResult.get(), listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
