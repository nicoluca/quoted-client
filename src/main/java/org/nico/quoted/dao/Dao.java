package org.nico.quoted.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.quoted.config.Config;
import org.nico.quoted.http.HttpService;

abstract class Dao<T> {
    protected Class<T> type;
    protected final String url;
    protected final HttpService httpService;
    protected final ObjectMapper objectMapper;

    public Dao(Class<T> type, String url) {
        this.type = type;
        this.url = url;
        this.httpService = Config.HTTP_SERVICE;
        this.objectMapper = new ObjectMapper();
        objectMapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public Dao(Class<T> type, String url, HttpService httpService) {
        this.type = type;
        this.url = url;
        this.httpService = httpService;
        this.objectMapper = new ObjectMapper();
        objectMapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
