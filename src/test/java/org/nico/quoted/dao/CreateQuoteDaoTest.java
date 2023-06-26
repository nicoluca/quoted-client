package org.nico.quoted.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateQuoteDaoTest {

    @Mock
    private HttpServiceImpl httpService = mock(HttpServiceImpl.class);

    @Test
    void createQuote() {
        when(httpService
                .post(anyString(), anyString()))
                .thenReturn(
                        TestUtil.resourceToString("/json/PostQuoteResponse.json")
                );
        Quote quoteToCreate = new Quote();
        quoteToCreate.setText("test");
        Book book = new Book();
        book.setId(1L);
        quoteToCreate.setSource(book);

        CreateQuoteDao createQuoteDao = new CreateQuoteDao(Quote.class, "http://localhost:8080", httpService);

        Quote createdQuote = createQuoteDao.create(quoteToCreate);

        assert createdQuote != null;
        assertEquals(1L, createdQuote.getId());
        assertEquals(1L, createdQuote.getSource().getId());
    }
}
