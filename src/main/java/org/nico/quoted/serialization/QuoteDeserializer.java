package org.nico.quoted.serialization;

import com.google.gson.*;
import org.nico.quoted.domain.Quote;

import java.lang.reflect.Type;
import java.util.List;

public class QuoteDeserializer implements JsonDeserializer<Quote>, ListDeserializer<Quote> {
    @Override
    public List<Quote> deserializeList(String json, Gson gson) {
        return null;
    }

    @Override
    public Quote deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
