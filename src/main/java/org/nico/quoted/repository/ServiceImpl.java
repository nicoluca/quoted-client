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
    public void create(T t) {
        String json = gson.toJson(t);
        httpService.post(url, json);
    }

    @Override
    public Optional<T> readById(long id) {
        String jsonResult = httpService.get(url + "/" + id);

        if (jsonResult == null)
            return Optional.empty();

        T t = gson.fromJson(jsonResult, type);
        return Optional.of(t);
    }

    @Override
    public List<T> readAll() {
        List<T> tList;

        try {
            HttpResponse response = HttpUtil.get(url);
            checkIfResponseIsOk(response);

            String jsonResult = response.getEntity().getContent().toString();
            tList = new JsonUtil<T>()
                    .deserializeList(jsonResult,
                            gson,
                            type.getSimpleName().toLowerCase() + "s",
                            type);

        } catch (IOException e) {
            log.warn("No " + type.getSimpleName() + "s could be retrieved.");
            throw new RuntimeException(e);
        }

        return tList;
    }


    @Override
    public void update(T t) {
        try {
            String json = gson.toJson(t);
            HttpResponse response = HttpUtil.put(url + "/" + t.getId(), json);
            checkIfResponseIsOk(response);
        } catch (IOException e) {
            log.error("Error while updating t: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T t) {
        try {
            HttpResponse response = HttpUtil.delete(url + "/" + t.getId());
            checkIfResponseIsOk(response);
        } catch (IOException e) {
            log.error("Error while deleting t: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void checkIfResponseIsOk(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    }
}