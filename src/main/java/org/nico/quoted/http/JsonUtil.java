package org.nico.quoted.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.nico.quoted.domain.Author;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil<T> {
    public List<T> deserializeList(String json, Gson gson, String listKey, Class<T> tClass) {
        List<T> tList = new ArrayList<>();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        JsonElement jsonList = jsonElement.getAsJsonObject()
                .get("_embedded").getAsJsonObject()
                .get(listKey).getAsJsonArray();

        for (JsonElement jsonT : jsonList.getAsJsonArray()) {
            T t = gson.fromJson(jsonT, tClass);
            tList.add(t);
        }
        return tList;
    }
}
