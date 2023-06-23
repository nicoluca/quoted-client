package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.http.HttpService;

@Slf4j
public class CreateDao<T> extends Dao<T> implements Create<T> {

    public CreateDao(Class<T> type, String url) {
        super(type, url);
    }

    // Used for unit testing, to inject a mock HttpService
    public CreateDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    public T create(T t) {
        try {
            String json = objectMapper.writeValueAsString(t);
            log.info("Posting {} with json {} to {}", t, json, url);
            String response = httpService.post(url, json);
            log.info("Response: {}", response);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
