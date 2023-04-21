package org.nico.quoted.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookRepositoryTest {
    private CRUDRepository<Book> bookRepository;
    private CRUDRepository<Author> authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        bookRepository = new RepositoryImplementation<>(Book.class, TestConfig.TEST_EMF);
        authorRepository = new RepositoryImplementation<>(Author.class, TestConfig.TEST_EMF);

        author = new Author("Test", "Test");
        authorRepository.create(author);
    }

    @AfterEach
    void tearDown() {
        bookRepository.readAll().forEach(bookRepository::delete);
        authorRepository.readAll().forEach(authorRepository::delete);
    }

    @Test
    void create() {
        Book book = new Book("Test book", author, null);
        bookRepository.create(book);

        assertEquals(book, bookRepository.readById(book.getId()).get());
    }

    @Test
    void readById() {
        assertEquals(Optional.empty(), bookRepository.readById(1L));
    }

    @Test
    void readAll() {
        assertEquals(0, bookRepository.readAll().size());

        int count = 10;
        IntStream.range(0, count).forEach(i -> {
            Book book = new Book("Test book", author, null);
            bookRepository.create(book);
        });

        assertEquals(count, bookRepository.readAll().size());
    }

    @Test
    void update() {
        Book book = new Book("Test book", author, null);
        bookRepository.create(book);

        String newTitle = "New title";
        book.setTitle(newTitle);
        bookRepository.update(book);

        assertEquals(newTitle, bookRepository.readById(book.getId()).get().getTitle());
    }

    @Test
    void delete() {
        Book book = new Book("Test book", author, null);
        bookRepository.create(book);

        bookRepository.delete(book);

        assertEquals(Optional.empty(), bookRepository.readById(book.getId()));
        assertEquals(0, bookRepository.readAll().size());
    }
}