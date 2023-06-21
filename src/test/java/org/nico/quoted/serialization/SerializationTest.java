package org.nico.quoted.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializeQuote() throws JsonProcessingException {
        Quote quote = Quote.builder()
                .text("TestQuote")
                .source(new Book("TestBook", new Author("TestFirstname", "TestLastname")))
                .build();

        quote.getSource().setId(1L);
        quote.setLastEdited(Timestamp.valueOf("2023-06-05 10:25:54"));
        String json = objectMapper.writeValueAsString(quote);

        JsonNode expected = objectMapper.readTree(TestUtil.resourceToString("/json/SerializedQuoteBook.json"));
        JsonNode actual = objectMapper.readTree(json);
        assertEquals(expected, actual);
    }

    @Test
    void serializeBook() throws JsonProcessingException {
        Book book = new Book("TestBook", new Author("TestFirstname", "TestLastname"));
        book.setIsbn("TestIsbn");
        String json = objectMapper.writeValueAsString(book);

         JsonNode expected = objectMapper.readTree(TestUtil.resourceToString("/json/SerializedBook.json"));
         JsonNode actual = objectMapper.readTree(json);
         assertEquals(expected, actual);
    }

    @Test
    void serializeArticle() throws JsonProcessingException {
        Article article = new Article("TestArticle", "TestUrl");
        article.setLastVisited(Timestamp.valueOf("2023-06-05 10:25:54"));
        String json = objectMapper.writeValueAsString(article);

        JsonNode expected = objectMapper.readTree(TestUtil.resourceToString("/json/SerializedArticle.json"));
        JsonNode actual = objectMapper.readTree(json);
        assertEquals(expected, actual);
    }
}
