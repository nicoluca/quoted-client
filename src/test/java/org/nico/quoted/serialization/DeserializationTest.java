package org.nico.quoted.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.Source;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/*
    * This test class tests the deserialization of the JSON responses from the API.
    * Serialization is implemented using Jackson annotations.
    * The JSON responses are stored in the resources/json folder.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeserializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @Order(1) // First test if sources are deserialized correctly, before checking quotes, as quotes depend on sources
    void testDeserializationSourceList() throws JsonProcessingException {
        String jsonString = TestUtil.resourceToString("/json/GetSources.json");
        List<Source> sources = objectMapper.readValue(jsonString, new TypeReference<>() {
        });

        assertEquals(6, sources.size());
        assertEquals(3, sources.stream().filter(source -> source instanceof Book).count());
        assertEquals(3, sources.stream().filter(source -> source instanceof Article).count());
        sources.forEach(source ->
                                assertNotEquals(0L, source.getId()));
        sources.stream()
                .filter(source -> source instanceof Book)
                .map(source -> (Book) source)
                .forEach(book ->
                        assertNotNull(book.getAuthor()));
    }

    @Test
    @Order(2)
    void testDeserializationQuoteList() throws JsonProcessingException {
        String jsonSources = TestUtil.resourceToString("/json/GetSources.json");
        String jsonQuotes = TestUtil.resourceToString("/json/GetQuotes.json");

        List<Source> sources = objectMapper.readValue(jsonSources, new TypeReference<>() {
        });
        Map<Long, Source> sourceMap = sources.stream()
                .collect(Collectors.toMap(Source::getId, source -> source));
        List<Quote> quotes = objectMapper.readValue(jsonQuotes, new TypeReference<>() {
        });

        assertEquals(6, quotes.size());
        quotes.forEach(quote -> {
            assertNotEquals(0L, quote.getId());
            assertNotNull(quote.getText());
            assertNotNull(quote.getSource());
            assertTrue(sourceMap.containsKey(quote.getSource().getId()));
        });

        quotes.forEach(quote -> quote.setSource(sourceMap.get(quote.getSource().getId())));

        quotes.forEach(quote -> {
            assertNotNull(quote.getSource().getTitle());
            assertTrue(quote.getSource() instanceof Book
                    || quote.getSource() instanceof Article);

            if (quote.getSource() instanceof Book book) {
                assertNotNull(book.getAuthor());
                assertNotNull(book.getAuthor().getFirstName());
                assertNotNull(book.getAuthor().getLastName());
            } else if (quote.getSource() instanceof Article article) {
                assertNotNull(article.getLastVisited());
                assertNotNull(article.getUrl());
            }
        });
    }
}
