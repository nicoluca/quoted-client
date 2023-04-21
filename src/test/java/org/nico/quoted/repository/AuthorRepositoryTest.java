package org.nico.quoted.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorRepositoryTest {

    /*
    persistence.xml needs to be set to 'create-drop' for testing
     */

    private CRUDRepository<Author> authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository = new RepositoryImplementation<>(Author.class, TestConfig.TEST_EMF);
    }

    @AfterEach
    void tearDown() {
        authorRepository.readAll().forEach(authorRepository::delete);
    }

    @Test
    @DisplayName("Create authors and confirm they are in database")
    void create() {
        Author author = new Author("Neil", "Stephenson");
        authorRepository.create(author);

        Author author2 = new Author("Neil", "Armstrong");
        authorRepository.create(author2);

        // Assert if author is in database
        assertEquals(authorRepository.readById(author.getId()).get(), author);
        assertEquals(2, authorRepository.readAll().size());
    }

    @Test
    @DisplayName("Read author by id")
    void readById() {
        Author author = new Author("Neil", "Stephenson");
        authorRepository.create(author);

        assertEquals(authorRepository.readById(author.getId()).get(), author);
    }

    @Test
    @DisplayName("Read all authors")
    void readAll() {
        Author author = new Author("Neil", "Stephenson");
        authorRepository.create(author);

        Author author2 = new Author("Neil", "Armstrong");
        authorRepository.create(author2);

        assertEquals(2, authorRepository.readAll().size());
    }

    @Test
    @DisplayName("Update author")
    void update() {
        Author author = new Author("Neil", "Stephenson");
        authorRepository.create(author);

        author.setFirstName("Neil deGrasse");
        authorRepository.update(author);

        assertEquals(authorRepository.readById(author.getId()).get(), author);
    }

    @Test
    @DisplayName("Delete author")
    void delete() {
        Author author = new Author("Neil", "Stephenson");
        authorRepository.create(author);

        authorRepository.delete(author);

        assertEquals(0, authorRepository.readAll().size());
    }

}