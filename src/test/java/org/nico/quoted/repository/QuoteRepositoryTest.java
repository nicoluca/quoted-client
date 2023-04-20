package org.nico.quoted.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import jakarta.persistence.RollbackException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuoteRepositoryTest {
    private QuoteRepository quoteRepository;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private ArticleRepository articleRepository;

    private Book book;
    private Article article;

    @BeforeEach
    void setUp() {
        quoteRepository = new QuoteRepository(TestConfig.TEST_EMF);
        bookRepository = new BookRepository(TestConfig.TEST_EMF);
        authorRepository = new AuthorRepository(TestConfig.TEST_EMF);
        articleRepository = new ArticleRepository(TestConfig.TEST_EMF);

        Author author = new Author("Test", "Test");
        authorRepository.create(author);
        book = new Book("Test book", author, null);
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
        Book bookTest = new Book("Test book", authorTest, null);
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
}