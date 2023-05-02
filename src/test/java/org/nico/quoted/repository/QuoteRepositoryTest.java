package org.nico.quoted.repository;

import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuoteRepositoryTest {
    private CRUDRepository<Quote> quoteRepository;
    private CRUDRepository<Book> bookRepository;
    private CRUDRepository<Author> authorRepository;
    private CRUDRepository<Article> articleRepository;

    private Book book;
    private Article article;

    @BeforeEach
    void setUp() {
        quoteRepository = new RepositoryImplementation<>(Quote.class, TestConfig.TEST_EMF);
        bookRepository = new RepositoryImplementation<>(Book.class, TestConfig.TEST_EMF);
        authorRepository = new RepositoryImplementation<>(Author.class, TestConfig.TEST_EMF);
        articleRepository = new RepositoryImplementation<>(Article.class, TestConfig.TEST_EMF);

        Author author = new Author("Test", "Test");
        authorRepository.create(author);
        book = new Book("Test book", author);
        bookRepository.create(book);
        article = new Article("Test article", "Test article");
        articleRepository.create(article);
    }

    // delete all quotes from the database
    @AfterEach
    void tearDown() {
        quoteRepository.readAll().forEach(quoteRepository::delete);
        bookRepository.readAll().forEach(bookRepository::delete);
        authorRepository.readAll().forEach(authorRepository::delete);
        articleRepository.readAll().forEach(articleRepository::delete);
    }

    @Test
    @DisplayName("Create quote for existing book")
    void createValidQuote() {
        Quote quote = new Quote("Test quote", book);
        quoteRepository.create(quote);

        assertEquals(quote, quoteRepository.readById(quote.getId()).get());
        assertEquals(1, quoteRepository.readAll().size());
    }

    @Test
    @DisplayName("Create quote for non-existing book")
    void createInvalidQuote() {
        Author authorTest = new Author("Test", "Test");
        Book bookTest = new Book("Test book", authorTest);
        Quote quote = new Quote("Test quote", bookTest);
        assertThrows(RollbackException.class, () -> quoteRepository.create(quote));
    }

    @Test
    void readById() {
        assertEquals(Optional.empty(), quoteRepository.readById(1L));
    }

    @Test
    void readAll() {
        Quote quote1 = new Quote("Test quote 1", book);
        Quote quote2 = new Quote("Test quote 2", book);
        Quote quote3 = new Quote("Test quote 3", article);

        quoteRepository.create(quote1);
        quoteRepository.create(quote2);
        quoteRepository.create(quote3);

        assertEquals(3, quoteRepository.readAll().size());
    }

    @Test
    void update() {
        Quote quote = new Quote("Test quote", book);
        quoteRepository.create(quote);

        quote.setText("Test quote updated");
        quoteRepository.update(quote);

        assertEquals("Test quote updated", quoteRepository.readById(quote.getId()).get().getText());
    }

    @Test
    void delete() {
        Quote quote = new Quote("Test quote", book);
        quoteRepository.create(quote);

        quoteRepository.delete(quote);

        assertEquals(Optional.empty(), quoteRepository.readById(quote.getId()));
    }

    @Test
    void testLastEdited() {
        Quote quote = new Quote("Test quote", book);
        quoteRepository.create(quote);

        assertEquals(quote.getLastEdited(), quoteRepository.readById(quote.getId()).get().getLastEdited());

        LocalDate today = LocalDate.now();
        LocalDate lastEdited = quote.getLastEdited().toLocalDateTime().toLocalDate();
        assertEquals(today, lastEdited);
    }

    @Test
    @DisplayName("Load test - create 1000 new quotes with books and authors, read all in under 1 second")
    void loadTest() {
        Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
            Author author = new Author("Test", "Test");
            authorRepository.create(author);
            Book book = new Book("Test Title", author);
            bookRepository.create(book);
            Quote quote = new Quote("Test quote " + i, book);
            quoteRepository.create(quote);
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        assertTrue(duration.toMillis() < 1000);
        assertEquals(1000, quoteRepository.readAll().size());
    }
}