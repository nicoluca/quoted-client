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
import org.nico.quoted.repository.*;

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
    private RepositoryModel repositoryModel = mock(RepositoryModel.class);
    @Mock
    private AuthorRepository authorRepository = mock(AuthorRepository.class);
    @Mock
    private BookRepository bookRepository = mock(BookRepository.class);
    @Mock
    private QuoteRepository quoteRepository = mock(QuoteRepository.class);
    @Mock
    private ArticleRepository articleRepository= mock(ArticleRepository.class);

    @BeforeEach
    void setUp() {
        when(repositoryModel.getAuthorRepository()).thenReturn(authorRepository);
        when(repositoryModel.getBookRepository()).thenReturn(bookRepository);
        when(repositoryModel.getQuoteRepository()).thenReturn(quoteRepository);
        when(repositoryModel.getArticleRepository()).thenReturn(articleRepository);

        // The model currently has 2 books by Tolkien and 1 article, 3 quotes each from a different source
        when(bookRepository.readAll()).thenReturn(BackendConstants.defaultBooks());
        when(articleRepository.readAll()).thenReturn(BackendConstants.defaultArticles());
        when(quoteRepository.readAll()).thenReturn(BackendConstants.defaultQuotes());

        model = new ClientModel(repositoryModel);

        author = new Author("Test", "Test");
        book = new Book("Test", author, "Test");
        quote = new Quote("Test", book);
    }


    @Test
    @DisplayName("Test if the model returns the correct number of sources")
    void getSources() {
        assertEquals(3, model.getSources().size());
    }

    @Test
    @DisplayName("Test if the model returns sources by index correctly")
    void getSourceByIndex() {
        assert model.getSourceByIndex(0) != null;
        assertEquals(BackendConstants.defaultBooks().get(0), model.getSourceByIndex(0));
    }

    @Test
    void getBooks() {
        assertEquals(2, model.getBooks().size());
    }

    @Test
    void getAuthors() {
        assertEquals(1, model.getAuthors().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes")
    void getQuotes() {
        assertEquals(3, model.getQuotes().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after adding a new quote")
    void addQuote() {
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