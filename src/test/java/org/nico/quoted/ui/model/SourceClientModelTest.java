package org.nico.quoted.ui.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.model.SourceClientModel;

import static org.junit.jupiter.api.Assertions.*;

class SourceClientModelTest {
    private SourceClientModel model;
    private Author author;
    private Book book;
    private Quote quote;

    @BeforeEach
    void setUp() {
        model = new SourceClientModel();
        author = new Author("Test", "Test");
        book = new Book("Test", author, "Test", "Test");
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
}