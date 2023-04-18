package org.nico.quoted.domain.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.repository.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientModelTest {
    private ClientModel model;
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

    @BeforeAll
    static void init() {
        mockStatic(EditViewModel.class);
    }

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
    @DisplayName("Test if the model returns the correct number of books")
    void getBooks() {
        assertEquals(2, model.getBooks().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct authors")
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
        Quote quote = new Quote("Test", model.getQuoteByIndex(0).getSource());
        Mockito.doNothing().when(quoteRepository).create(quote);
        model.addQuote(quote);
        assertEquals(4, model.getQuotes().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after deleting a quote")
    void deleteQuoteByIndex() {
        Mockito.doNothing().when(quoteRepository).delete(model.getQuoteByIndex(0));
        model.deleteQuoteByIndex(0);
        assertEquals(2, model.getQuotes().size());
    }

    @Test
    @DisplayName("Test if the model returns the correct quote by index")
    void getQuoteByIndex() {
        assertNotNull(model.getQuoteByIndex(0));
        assertEquals(BackendConstants.defaultQuotes().get(0), model.getQuoteByIndex(0));
    }

    @Test
    @DisplayName("Test returning a random quote")
    void getRandomQuote() {
        assertNotNull(model.getRandomQuote());
    }

    @Test
    @DisplayName("Test updating a quote")
    void updateQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(model.getQuoteByIndex(0));
        Quote quote = new Quote("Test", model.getQuoteByIndex(0).getSource());
        model.updateQuote(quote);

        assertEquals("Test", model.getQuoteByIndex(0).getText());
        assertEquals(3, model.getQuotes().size());
    }

    @Test
    @DisplayName("Test searching sources by text")
    void searchSources() {
        assertEquals(2, model.searchSources("Tolkien").size());
    }

    @Test
    @DisplayName("Test searching quotes by text")
    void searchQuotes() {
        assertEquals(1, model.searchQuotes("Lorem").size());
        assertEquals(2, model.searchQuotes("Tolkien").size());
    }

    @Test
    @DisplayName("Test filtering quotes by source")
    void getQuotesBySource() {
        assertEquals(1, model.getQuotesBySource(model.getSourceByIndex(0)).size());
        Quote quote = new Quote("Test", model.getSourceByIndex(0));
        model.addQuote(quote);
        assertEquals(2, model.getQuotesBySource(model.getSourceByIndex(0)).size());
    }

    @Test
    @DisplayName("Test adding a new book")
    void addBook() {
        Book book = new Book("Test", new Author("Test", "Test"), "Test");
        Mockito.doNothing().when(bookRepository).create(book);
        model.addBook(book);
        assertEquals(4, model.getSources().size());
        assertEquals(3, model.getBooks().size());
    }

    @Test
    @DisplayName("Test updating sources")
    void updateSource() {
        updateBook();
        updateArticle();
    }

    private void updateBook() {
        when(EditViewModel.getSourceToEdit()).thenReturn(model.getSourceByIndex(0));

        assert model.getSourceByIndex(0) instanceof Book;
        Book bookToUpdate = (Book) model.getSourceByIndex(0);
        Book updatingBook = new Book("Test", bookToUpdate.getAuthor(), "Test");
        Mockito.doNothing().when(bookRepository).update(bookToUpdate);


        model.updateSource(updatingBook);
        assertEquals("Test", model.getSourceByIndex(0).getTitle());
        assertEquals(3, model.getSources().size());
    }

    private void updateArticle() {
        when(EditViewModel.getSourceToEdit()).thenReturn(model.getSourceByIndex(2));

        assert model.getSourceByIndex(2) instanceof Article;
        Article articleToUpdate = (Article) model.getSourceByIndex(2);
        Article updatingArticle = new Article("Test", "https://www.test.com");
        Mockito.doNothing().when(articleRepository).update(articleToUpdate);

        model.updateSource(updatingArticle);
        assertEquals("Test", model.getSourceByIndex(2).getTitle());
        assertEquals("https://www.test.com", ((Article) model.getSourceByIndex(2)).getUrl());
        assertEquals(3, model.getSources().size());
    }

    @Test
    @DisplayName("Test deleting sources")
    void deleteSourceByIndex() {
        model.deleteSourceByIndex(0);
        assertEquals(2, model.getSources().size());
    }
}