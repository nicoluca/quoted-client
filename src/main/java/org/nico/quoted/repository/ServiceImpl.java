package org.nico.quoted.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.nico.quoted.config.DBConfig;
import org.nico.quoted.domain.Identifiable;
import org.nico.quoted.http.HttpUtil;
import org.nico.quoted.http.JsonUtil;

@Slf4j
public class ServiceImpl<T extends Identifiable> implements CrudService<T> {
    private final Class<T> type;
    private final String url;
    private final Gson gson;
    private final HttpService httpService;

    public ServiceImpl(Class<T> type, String url, JsonDeserializer<T> jsonDeserializer, HttpService httpService) {
        this.type = type;
        this.url = url;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(type, jsonDeserializer)
                .create();
        this.httpService = httpService;
    }

    @Override
    public T create(T t) {
        String json = gson.toJson(t);
        String response = httpService.post(url, json);
        return gson.fromJson(response, type);
    }

    @Override
    public Optional<T> readById(long id) {
        Optional<String> jsonResult = httpService.get(url + "/" + id);

        if (jsonResult.isEmpty())
            return Optional.empty();

        T t = gson.fromJson(jsonResult.get(), type);
        return Optional.of(t);
    }

    @Override
    public List<T> readAll() {
        Optional<String> jsonResult = httpService.get(url);

        if (jsonResult.isEmpty())
            return List.of();

        return new JsonUtil<T>()
                .deserializeList(jsonResult.get(),
                        gson,
                        type.getSimpleName().toLowerCase() + "s",
                        type);
    }


    @Override
    public T update(T t) {
        String json = gson.toJson(t);
        String response = httpService.put(url + "/" + t.getId(), json);
        return gson.fromJson(response, type);
    }

    @Override
    public void delete(T t) {
        httpService.delete(url + "/" + t.getId());
    }

    private void checkIfResponseIsOk(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    }
}