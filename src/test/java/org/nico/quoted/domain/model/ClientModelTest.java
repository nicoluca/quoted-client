package org.nico.quoted.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.*;
import org.nico.quoted.model.ClientModel;
import org.nico.quoted.model.ServiceModel;
import org.nico.quoted.service.ArticleService;
import org.nico.quoted.service.BookService;
import org.nico.quoted.service.QuoteService;
import org.nico.quoted.service.SourceService;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientModelTest {

    private ClientModel model;

    @Mock
    private ServiceModel serviceModel = mock(ServiceModel.class);
    @Mock
    private ArticleService articleService = mock(ArticleService.class);
    @Mock
    private BookService bookService = mock(BookService.class);
    @Mock
    private QuoteService quoteService = mock(QuoteService.class);
    @Mock
    private SourceService sourceService = mock(SourceService.class);


    @BeforeEach
    void setUp() {
        when(serviceModel.getArticleService()).thenReturn(articleService);
        when(serviceModel.getQuoteService()).thenReturn(quoteService);
        when(serviceModel.getBookService()).thenReturn(bookService);
        when(serviceModel.getSourceService()).thenReturn(sourceService);

        // The test model has 2 books by 1 author and 1 article; 3 quotes each from a different source.
        List<Source> sources = new ArrayList<>();
        sources.addAll(TestConfig.defaultBooks());
        sources.addAll(TestConfig.defaultArticles());

        when(sourceService.readAll()).thenReturn(sources);
        when(quoteService.readAll()).thenReturn(TestConfig.defaultQuotes());

        model = new ClientModel(serviceModel);
    }

    // ########## BASE TESTS ##########

    @Test
    @DisplayName("Test if the model returns the correct number of sources")
    void getSources() {
        assertEquals(3, numberOfSources());
    }

    @Test
    @DisplayName("Test if the model returns sources by index correctly")
    void getSourceByIndex() {
        assert firstBook() != null;
        assertEquals(TestConfig.defaultBooks().get(0), firstBook());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of books")
    void getBooks() {
        assertEquals(2, numberOfBooks());
    }

    @Test
    @DisplayName("Test if the model returns the correct authors")
    void getAuthors() {
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes")
    void getQuotes() {
        assertEquals(3, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after adding a new quote")
    void addQuote() {
        Quote quote = new Quote("Test", firstBook());

        when(quoteService.create(any(Quote.class))).thenReturn(quote);
        model.addQuote(quote);
        assertEquals(4, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after deleting a quote")
    void deleteQuoteByIndex() {
        Mockito.doNothing().when(quoteService).delete(firstQuote());
        model.deleteQuoteByIndex(0);
        assertEquals(2, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct quote by index")
    void getQuoteByIndex() {
        assertNotNull(firstQuote());
        assertEquals(TestConfig.defaultQuotes().get(0), firstQuote());
    }

    @Test
    @DisplayName("Test returning a random quote")
    void getRandomQuote() {
        when(quoteService.readRandomQuote()).thenReturn(firstQuote());
        assertNotNull(model.getRandomQuote());
    }

    @Test
    @DisplayName("Test updating a quote")
    void updateQuote() {
        model.setQuoteToEdit(firstQuote());
        model.setSourceToEdit(firstSource());
        Quote quote = new Quote("Test", firstSource());
        model.updateQuote(quote);

        assertEquals("Test", firstQuote().getText());
        assertEquals(3, numberOfQuotes());
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
        assertEquals(1, model.getQuotesBySource(firstSource()).size());
        Quote quote = new Quote("Test", firstSource());
        model.addQuote(quote);
        assertEquals(2, model.getQuotesBySource(firstSource()).size());
    }

    @Test
    @DisplayName("Test adding a new book")
    void addBook() {
        Book book = new Book("Test", new Author("Test", "Test"));
        when(bookService.create(any(Book.class))).thenReturn(book);
        model.addBook(book);
        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
    }

    @Test
    @DisplayName("Test updating sources")
    void updateSource() {
        updateBook();
        updateArticle();
    }

    private void updateBook() {
        model.setSourceToEdit(firstSource());

        assert firstSource() instanceof Book;
        Book bookToUpdate = firstBook();
        Book updatingBook = new Book("Test", bookToUpdate.getAuthor());
        when(bookService.update(bookToUpdate)).thenReturn(new Book("Test", bookToUpdate.getAuthor()));


        model.updateSource(updatingBook);
        assertEquals("Test", firstSource().getTitle());
        assertEquals(3, numberOfSources());
        assertEquals(2, numberOfBooks());

        verify(bookService, times(1)).update(updatingBook);
    }

    private void updateArticle() {
        model.setSourceToEdit(firstArticle());

        assert model.getSourceByIndex(2).equals(firstArticle());
        Article articleToUpdate = firstArticle();
        Article updatingArticle = new Article("Test", "https://www.test.com");
        when(articleService.update(articleToUpdate)).thenReturn(new Article("Test", "https://www.test.com"));

        model.updateSource(updatingArticle);
        assertEquals("Test", firstArticle().getTitle());
        assertEquals("https://www.test.com", firstArticle().getUrl());
        assertEquals(3, numberOfSources());
        assertEquals(1, numberOfArticles());

        verify(articleService, times(1)).update(updatingArticle);
    }

    @Test
    @DisplayName("Test deleting sources")
    void deleteSourceByIndex() {
        Book source = (Book) model.getSourceByIndex(0);
        doNothing().when(sourceService).delete(source);

        model.deleteSourceByIndex(0);
        assertEquals(2, numberOfSources());
        assertEquals(1, numberOfBooks());
        assertEquals(1, numberOfArticles());

        verify(sourceService, times(1)).delete(source);
    }

    // ######### EDGE CASES #########
    @Test
    @DisplayName("Test: Change the book of a quote by changing its title")
    void changeBookTitleOfQuote() {
        when(quoteService.update(any(Quote.class))).thenReturn(firstQuote());
        when(bookService.update(any(Book.class))).thenReturn(firstBook());

        model.setQuoteToEdit(firstQuote());
        Quote quote = firstQuote();
        Book book = (Book) quote.getSource();

        book.setTitle("Test");
        model.updateSource(book);
        model.updateQuote(quote);

        assertEquals("Test", firstQuote().getSource().getTitle());

        verify(quoteService, times(1)).update(quote);
        verify(bookService, times(1)).update(book);
    }

    @Test
    @DisplayName("Test: Change the book of a quote by changing its author to a new author")
    void changeAuthorOfBookOfQuote() {
        model.setQuoteToEdit(firstQuote());
        Quote quote = firstQuote();
        Book book = (Book) quote.getSource();

        book.setAuthor(new Author("Test", "Test"));
        model.updateSource(book);
        model.updateQuote(quote);

        assertTrue(model.getAuthors().stream().anyMatch(author -> author.equals(new Author("Test", "Test"))));
        assertEquals(2, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Change author of existing book to new author")
    void changeAuthorOfExistingBook() {
        model.setSourceToEdit(firstSource());
        firstBook().setAuthor(new Author("Max", "Frisch"));
        model.updateSource(firstBook());

        assertEquals(2, numberOfAuthors());
        assertTrue(model.getAuthors().stream().anyMatch(author -> author.equals(new Author("Max", "Frisch"))));
    }

    @Test
    @DisplayName("Test: Add a book with an existing author (object)")
    void addBookWithExistingAuthor() {
        Author author = firstBook().getAuthor();
        Book book = new Book("Test", author);

        when(bookService.create(any(Book.class))).thenReturn(book);
        model.addBook(book);

        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Add a book with an existing author (String)")
    void addBookWithExistingAuthorString() {
        Author author = new Author("J.R.R.", "Tolkien");
        Book book = new Book("Test", author);

        when(bookService.create(any(Book.class))).thenReturn(book);
        model.addBook(book);

        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Add a quote with a new book with an existing author (String)")
    void addQuoteWithBookWithExistingAuthor() {
        Author author = new Author("J.R.R.", "Tolkien");
        Book book = new Book("Test", author);
        Quote quote = new Quote("Test", book);


        when(bookService.create(book)).thenReturn(book);
        when(quoteService.create(quote)).thenReturn(quote);

        model.addQuote(quote);
        assertEquals(4, numberOfQuotes());
        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Change the article of a quote by replacing it with a new article")
    void changeArticleOfQuote() {
        model.setQuoteToEdit(thirdQuote());
        Quote quote = thirdQuote();
        Article newArticle = new Article("Test", "https://www.test.com");
        quote.setSource(newArticle);
        model.updateQuote(quote);

        assertEquals("https://www.test.com", ((Article) thirdQuote().getSource()).getUrl());
        assertEquals(4, numberOfSources());
        assertEquals(2, numberOfArticles());
    }

    @Test
    @DisplayName("Test: Delete a book with a quote")
    void deleteBookWithQuote() {
        model.deleteSourceByIndex(0);
        assertEquals(2, numberOfSources());
        assertEquals(2, numberOfQuotes());
        assertEquals(1, numberOfBooks());
    }

    @Test
    @DisplayName("Test: Delete an article with a quote")
    void deleteArticleWithQuote() {
        model.deleteSourceByIndex(2);
        assertEquals(2, numberOfSources());
        assertEquals(2, numberOfQuotes());
        assertEquals(0, numberOfArticles());
    }

    @Test
    @DisplayName("Change source of quote from book to article")
    void changeSourceOfQuoteFromBookToArticle() {
        model.setQuoteToEdit(firstQuote());
        model.setSourceToEdit(firstQuote().getSource());
        Quote quote = firstQuote();
        assert quote.getSource() instanceof Book;
        Article article = new Article("Test", "https://www.test.com");
        quote.setSource(article);

        model.updateQuote(quote);

        assertTrue(quote.getSource() instanceof Article);
        assertEquals(4, numberOfSources());
        assertEquals(2, numberOfArticles());
        assertEquals(2, numberOfBooks());
    }


    // ############################## Load test ##############################

    @Test
    @DisplayName("Test: Create 1000 quotes with 1000 sources in under 1 second")
    void create1000QuotesWith1000Sources() {
        Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
            model.addQuote(new Quote("Test", new Book("Test", new Author("Test", "Test"))));
        }
        Instant end = Instant.now();

        long durationInMillis = Duration.between(start, end).toMillis();
        assertTrue(durationInMillis < 1000);
    }

    // ############################## Helper methods ##############################

    private Quote firstQuote() {
        return model.getQuoteByIndex(0);
    }

    private Quote thirdQuote() {
        return model.getQuoteByIndex(2);
    }

    private Source firstSource() {
        return model.getSourceByIndex(0);
    }

    private Book firstBook() {
        return (Book) model.getSourceByIndex(0);
    }

    private Article firstArticle() {
        return (Article) model.getSourceByIndex(2);
    }

    private int numberOfSources() {
        return model.getSources().size();
    }

    private int numberOfBooks() {
        return model.getBooks().size();
    }

    private int numberOfArticles() {
        return model.getArticles().size();
    }

    private int numberOfAuthors() {
        return model.getAuthors().size();
    }

    private int numberOfQuotes() {
        return model.getQuotes().size();
    }
}

