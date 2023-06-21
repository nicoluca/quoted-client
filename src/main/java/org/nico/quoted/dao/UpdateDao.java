package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nico.quoted.domain.Identifiable;
import org.nico.quoted.http.HttpService;

public class UpdateDao<T extends Identifiable> extends Dao<T> implements Update<T> {
    public UpdateDao(Class<T> type, String url) {
        super(type, url);
    }

    public UpdateDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public T update(T t) {
        try {
            String json = objectMapper.writeValueAsString(t);
            String response = httpService.put(url + "/" + t.getId(), json);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

