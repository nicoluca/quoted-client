package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nico.quoted.http.HttpService;

public class CreateDao<T> extends Dao<T> implements Create<T> {

    public CreateDao(Class<T> type, String url) {
        super(type, url);
    }

    public CreateDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    public T create(T t) {
        try {
            String json = objectMapper.writeValueAsString(t);
            String response = httpService.post(url, json);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
