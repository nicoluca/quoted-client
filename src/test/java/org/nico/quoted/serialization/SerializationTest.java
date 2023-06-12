package org.nico.quoted.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializeQuote() {

    }

    @Test
    void serializeBook() throws JsonProcessingException {
        Book book = new Book("TestBook", new Author("TestFirstname", "TestLastname"));
        book.setIsbn("TestIsbn");
        String json = objectMapper.writeValueAsString(book);

        // Compare the json objects using Jackson
         JsonNode expected = objectMapper.readTree(TestUtil.resourceToString("/json/SerializedBook.json"));
         JsonNode actual = objectMapper.readTree(json);
         assertEquals(expected, actual);
    }

    @Test
    void serializeArticle() {

    }
}
