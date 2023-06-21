package org.nico.quoted.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nico.quoted.TestUtil;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CreateDaoTest {

    @Mock
    private HttpServiceImpl httpService = mock(HttpServiceImpl.class);

    @Test
    void createBook() {
        when(httpService
                .post(anyString(), anyString()))
                .thenReturn(
                        TestUtil.resourceToString("/json/PostBookResponse.json")
                );

        CreateDao<Book> createDao = new CreateDao<>(Book.class, Config.BOOKS_URL, httpService);

        Book bookToCreate = TestUtil.createBook();
        Book createdBook = createDao.create(bookToCreate);

        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(bookToCreate.getTitle(), createdBook.getTitle());

        verify(httpService, times(1))
                .post(anyString(), anyString());
    }

    @Test
    void createQuote() {
        when(httpService
                .post(anyString(), anyString()))
                .thenReturn(
                        TestUtil.resourceToString("/json/PostQuoteResponse.json")
                );

        CreateDao<Quote> createDao = new CreateDao<>(Quote.class, Config.QUOTES_URL, httpService);

        Quote quoteToCreate = TestUtil.createQuote();
        Quote createdQuote = createDao.create(quoteToCreate);


        assertNotNull(createdQuote);
        assertEquals(1L, createdQuote.getId());
        assertEquals(createdQuote.getText(), quoteToCreate.getText());
        assertEquals(1L, createdQuote.getSource().getId());

        verify(httpService, times(1))
                .post(anyString(), anyString());
    }
}
