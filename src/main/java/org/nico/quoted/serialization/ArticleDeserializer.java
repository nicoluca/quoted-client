package org.nico.quoted.serialization;

import com.google.gson.*;
import org.nico.quoted.domain.Article;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class ArticleDeserializer implements JsonDeserializer<Article> {

    @Override
    public Article deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String title = jsonObject.get("title").getAsString();
        String url = jsonObject.get("url").getAsString();
        String timestampString = jsonObject.get("lastVisited").getAsString();
        timestampString = timestampString.replace("T", " ");
        // Delete +00:00
        timestampString = timestampString.substring(0, timestampString.length() - 6);
        Timestamp timestamp = Timestamp.valueOf(timestampString);

        Article article = new Article(title, url);
        article.setId(id);
        article.setLastVisited(timestamp);
        return article;
    }
}
