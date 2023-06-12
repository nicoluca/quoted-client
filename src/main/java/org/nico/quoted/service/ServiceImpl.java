package org.nico.quoted.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Identifiable;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ServiceImpl<T extends Identifiable> implements CrudService<T> {
    private final Class<T> type;
    private final String url;
    private final HttpService httpService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServiceImpl(Class<T> type, String url, HttpService httpService) {
        this.type = type;
        this.url = url;
        this.httpService = httpService;
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public T create(T t) {
        try {
            String json = objectMapper.writeValueAsString(t);
            String response = httpService.post(url, json);
            return objectMapper.convertValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> readAll() {
        Optional<String> jsonResult = httpService.get(url);

        if (jsonResult.isEmpty())
            return List.of();

        try {
            return objectMapper.readValue(jsonResult.get(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public T update(T t) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(t);
            String response = httpService.put(url + "/" + t.getId(), json);
            return objectMapper.convertValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T t) {
        httpService.delete(url + "/" + t.getId());
    }
}