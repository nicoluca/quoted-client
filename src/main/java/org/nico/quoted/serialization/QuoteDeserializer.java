package org.nico.quoted.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.nico.quoted.domain.Quote;

import java.lang.reflect.Type;

public class QuoteDeserializer implements JsonDeserializer<Quote> {

    @Override
    public Quote deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null; // TODO
    }
}
