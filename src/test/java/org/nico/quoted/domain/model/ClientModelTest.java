package org.nico.quoted.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.repository.AuthorRepository;
import org.nico.quoted.repository.QuoteRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientModelTest {
    private ClientModel model;
    private Author author;
    private Book book;
    private Quote quote;
    // TODO: Complete the tests
    // TODO Increase test coverage beyond basic functionality by adding quotes with same source, author, book, etc., and testing for correct number of books etc. returned
    @Mock
    private AuthorRepository authorRepository;
//    @Mock
//    private BookRepository bookRepository;
    @Mock
    private QuoteRepository quoteRepository;
//    @Mock
//    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        // The model currently has 2 books by Tolkien and 1 article, 3 quotes each from a different source
        model = new ClientModel();
        author = new Author("Test", "Test");
        book = new Book("Test", author, "Test");
        quote = new Quote("Test", book);
        authorRepository = new AuthorRepository();
        quoteRepository = mock(QuoteRepository.class);
    }


    @Test
    void getSources() {

    }

    @Test
    void getSourceByIndex() {
    }

    @Test
    void getBooks() {
    }

    @Test
    void getAuthors() {
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes")
    void getQuotes() {
        when(quoteRepository.readAll()).thenReturn(BackendConstants.defaultQuotes());
        assertEquals(3, model.getQuotes().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after adding a new quote")
    void addQuote() {
        when(quoteRepository.readAll()).thenReturn(BackendConstants.defaultQuotes());
        Mockito.doNothing().when(quoteRepository).create(quote);
        model.addQuote(quote);
        assertEquals(4, model.getQuotes().size());
    }

    @Test
    void deleteQuoteByIndex() {
    }

    @Test
    void getQuoteByIndex() {
    }

    @Test
    void getRandomQuote() {
    }

    @Test
    void updateQuote() {
    }

    @Test
    void searchSources() {
    }

    @Test
    void searchQuotes() {
    }

    @Test
    void getQuotesBySource() {
    }

    @Test
    void addBook() {
    }

    @Test
    void updateSource() {
    }

    @Test
    void deleteSourceByIndex() {
    }
}