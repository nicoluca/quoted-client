package org.nico.quoted.serialization;

import com.google.gson.*;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookDeserializer implements JsonDeserializer<Book> {
    @Override
    public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String title = jsonObject.get("title").getAsString();
        String isbn = jsonObject.get("isbn").getAsString();
        String coverPath = jsonObject.get("coverPath").getAsString();
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setCoverPath(coverPath);
        return book;
    }

    public static List<Book> parseBooks(String json, Gson gson, Author author) {
        List<Book> books = new ArrayList<>();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        JsonElement jsonBooks = jsonElement.getAsJsonObject()
                .get("_embedded").getAsJsonObject()
                .get("books").getAsJsonArray();

        for (JsonElement jsonBook : jsonBooks.getAsJsonArray()) {
            Book book = gson.fromJson(jsonBook, Book.class);
            book.setAuthor(author);
            books.add(book);
        }
        return books;
    }
}