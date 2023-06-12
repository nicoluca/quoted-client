package org.nico.quoted.service;

import com.google.gson.JsonDeserializer;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.*;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AuthorServiceTest {

    private CrudService<Author> authorService;

    @BeforeEach
    void setUp() {

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
    @DisplayName("Write authors, read all and confirm they are saved in backend")
    @Disabled("Needs a running server")
    void readAll() {
        Author author = new Author("Neil", "Stephenson");
        authorService.create(author);
        Author author2 = new Author("Neil", "Armstrong");
        authorService.create(author2);

        assertEquals(2, authorService.readAll().size());
    }

}