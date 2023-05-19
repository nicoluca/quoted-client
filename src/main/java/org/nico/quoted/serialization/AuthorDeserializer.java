package org.nico.quoted.serialization;

import com.google.gson.*;
import org.nico.quoted.domain.Author;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AuthorDeserializer implements JsonDeserializer<Author> {
    @Override
    public Author deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        Author author = new Author(firstName, lastName);
        author.setId(id);
        return author;
    }
}
