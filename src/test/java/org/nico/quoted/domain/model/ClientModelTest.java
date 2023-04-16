package org.nico.quoted.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import static org.junit.jupiter.api.Assertions.*;

class ClientModelTest {
    private ClientModel model;
    private Author author;
    private Book book;
    private Quote quote;

    @BeforeEach
    void setUp() {
        model = new ClientModel();
        author = new Author("Test", "Test");
        book = new Book("Test", author, "Test");
        quote = new Quote("Test", book);
    }

    @Test
    void getSources() {
        model.getSources().add(book);
        assertTrue(model.getSources().contains(book));
    }

    @Test
    void getBooks() {
        model.getSources().add(book);
        assertTrue(model.getBooks().contains(book));
    }

    @Test
    void getAuthors() {
        model.getSources().add(book);
        assertTrue(model.getAuthors().contains(author));
    }

    @Test
    void getQuotes() {
        model.getQuotes().add(quote);
        assertTrue(model.getQuotes().contains(quote));
    }


    @Test
    void getRandomQuote() {
        assertTrue(model.getRandomQuote() instanceof Quote);
        assertFalse(model.getRandomQuote().getText().isEmpty());
    }

    @Test
    @DisplayName("Search for sources by String")
    void searchSources() {
        model.addQuote(quote);
        assertTrue(model.searchSources("Test").contains(book));
        assertFalse(model.searchSources("Some very long String").contains(book));
    }

    @Test
    @DisplayName("Search for quotes by String")
    void searchQuotes() {
        model.addQuote(quote);
        assertTrue(model.searchQuotes("Test").contains(quote));
        assertFalse(model.searchQuotes("Some very long String").contains(quote));
    }

    @Test
    @DisplayName("Get quotes by source")
    void getQuotesBySource() {
        model.addQuote(quote);
        assertTrue(model.getQuotesBySource(book).contains(quote));
        assertFalse(model.getQuotesBySource(new Book("Some title", author, "Cover")).contains(quote));
    }
}