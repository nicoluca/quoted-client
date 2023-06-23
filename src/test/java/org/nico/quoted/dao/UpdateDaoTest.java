package org.nico.quoted.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nico.quoted.TestUtil;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Book;
import org.nico.quoted.http.HttpServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UpdateDaoTest {

    @Mock
    private HttpServiceImpl httpService = mock(HttpServiceImpl.class);

    @Test
    void update() {
        when(httpService
                .put(anyString(), anyString()))
                .thenReturn(
                        TestUtil.resourceToString("/json/PostBookResponse.json")
                );

        UpdateDao<Book> updateDao = new UpdateDao<>(Book.class, Config.BOOKS_URL, httpService);

        Book bookToUpdate = TestUtil.createBook();
        bookToUpdate.setId(1L);

        Book updatedBook = updateDao.update(bookToUpdate);

        assertEquals(bookToUpdate, updatedBook);
        verify(httpService, times(1))
                .put(anyString(), anyString());
    }
}