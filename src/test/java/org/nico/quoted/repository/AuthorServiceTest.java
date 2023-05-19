package org.nico.quoted.repository;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Author;
import org.nico.quoted.serialization.AuthorDeserializer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AuthorServiceTest {

    private CrudService<Author> authorService;

    @BeforeEach
    void setUp() {
        JsonDeserializer<Author> authorDeserializer = new AuthorDeserializer();
        authorService = new ServiceImpl<>(Author.class,
                Config.AUTHORS_URL,
                authorDeserializer,
                Config.HTTP_SERVICE);

        // Mocks
        // TODO - move to @BeforeAll, currently tests against real backend
        HttpClient httpClient = mock(HttpClient.class);
        HttpGet httpGet = mock(HttpGet.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
    }

    @AfterEach
    void tearDown() {
        authorService.readAll().forEach(authorService::delete);
    }

    @Test
    @DisplayName("Create authors and confirm they are saved in backend")
    void createAndRetrieve() {
        Author author = new Author("Neil", "Stephenson");
        author = authorService.create(author);

        Author author2 = new Author("Neil", "Armstrong");
        author2 = authorService.create(author2);

        // Assert if author is in database
        Optional<Author> authorOptional = authorService.readById(author.getId());
        assert authorOptional.isPresent();
        assertEquals(authorOptional.get(), author);

        // Assert if author2 is in database
        Optional<Author> author2Optional = authorService.readById(author2.getId());
        assert author2Optional.isPresent();
        assertEquals(author2Optional.get(), author2);
    }

    @Test
    @DisplayName("Write authors, read all and confirm they are saved in backend")
    void readAll() {
        Author author = new Author("Neil", "Stephenson");
        authorService.create(author);
        Author author2 = new Author("Neil", "Armstrong");
        authorService.create(author2);

        assertEquals(2, authorService.readAll().size());
    }

    @Test
    @DisplayName("Update author")
    void update() {
        Author author = new Author("OldName", "Stephenson");
        author = authorService.create(author);

        author.setFirstName("NewName");
        Author newAuthor = authorService.update(author);

        assertEquals(author, newAuthor);

        Optional<Author> authorOptional = authorService.readById(author.getId());
        assert authorOptional.isPresent();
        assertEquals(authorOptional.get(), author);
    }

    @Test
    @DisplayName("Delete author")
    void delete() {
        Author author = new Author("Neil", "Stephenson");
        author = authorService.create(author);
        authorService.delete(author);
        Optional<Author> authorOptional = authorService.readById(author.getId());
        assertTrue(authorOptional.isEmpty());
    }

}