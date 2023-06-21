package org.nico.quoted.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nico.quoted.TestUtil;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.Source;
import org.nico.quoted.http.HttpServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadAllDaoTest {

    @Mock
    private HttpServiceImpl httpService = mock(HttpServiceImpl.class);

    @Test
    void readAllSources() {
        when(httpService
                .get(anyString()))
                .thenReturn(Optional.of(
                        TestUtil.resourceToString("/json/GetSources.json")
                ));

        ReadAllDao<Source> readAllDao = new ReadAllDao<>(Source.class, Config.SOURCE_URL, httpService);

        List<Source> sources = readAllDao.readAll();

        assertEquals(6, sources.size());
        assertEquals(3, sources.stream()
                .filter(source -> source instanceof Book)
                .count());
        assertEquals(3, sources.stream()
                .filter(source -> source instanceof Article)
                .count());
        sources.forEach(source ->
                assertNotEquals(0L, source.getId()));
        sources.stream()
                .filter(source -> source instanceof Book)
                .map(source -> (Book) source)
                .forEach(book ->
                        assertNotNull(book.getAuthor()));
    }

    @Test
    void readAllQuotes() {
        when(httpService
                .get(anyString()))
                .thenReturn(Optional.of(
                        TestUtil.resourceToString("/json/GetQuotes.json")
                ));

        ReadAllDao<Quote> readAllDao = new ReadAllDao<>(Quote.class, Config.QUOTES_URL, httpService);

        List<Quote> quotes = readAllDao.readAll();

        assertEquals(6, quotes.size());
        quotes.forEach(quote -> {
            assertNotEquals(0L, quote.getId());
            assertNotNull(quote.getSource());
            assertNotEquals(0L, quote.getSource().getId());
            assertNotNull(quote.getText());

        });
    }
}