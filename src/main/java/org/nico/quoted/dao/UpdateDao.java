package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Identifiable;
import org.nico.quoted.http.HttpService;

@Slf4j
public class UpdateDao<T extends Identifiable> extends Dao<T> implements Update<T> {
    public UpdateDao(Class<T> type, String url) {
        super(type, url);
    }

    // Used for unit testing, to inject a mock HttpService
    public UpdateDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public T update(T t) {
        try {
            String json = objectMapper.writeValueAsString(t);
            log.info("Putting {} with json {} to {}", t, json, url);
            String response = httpService.put(url + "/" + t.getId(), json);
            log.info("Response: {}", response);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

