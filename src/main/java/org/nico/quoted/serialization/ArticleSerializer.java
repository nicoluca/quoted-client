package org.nico.quoted.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.nico.quoted.domain.Article;

import java.lang.reflect.Type;

public class ArticleSerializer implements JsonSerializer<Article> {
    @Override
    public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", src.getTitle());
        jsonObject.addProperty("url", src.getUrl());
        jsonObject.addProperty("type", "article");
        // Timestamp in format "2023-05-19T21:10:35.762+00:00", UTC
        if (src.getLastVisited() != null)
            jsonObject.addProperty("lastVisited",
                    src.getLastVisited().toString().replace(" ", "T"));
        return jsonObject;
    }
}
