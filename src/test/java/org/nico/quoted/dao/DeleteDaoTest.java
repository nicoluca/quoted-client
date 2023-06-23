package org.nico.quoted.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nico.quoted.TestUtil;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Book;
import org.nico.quoted.http.HttpServiceImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DeleteDaoTest {

    @Mock
    private HttpServiceImpl httpService = mock(HttpServiceImpl.class);

    @Test
    void delete() {
        doNothing().when(httpService).delete(anyString());

        DeleteDao<Book> deleteDao = new DeleteDao<>(Book.class, Config.BOOKS_URL, httpService);
        deleteDao.delete(TestUtil.createBook());

        verify(httpService, times(1)).delete(anyString());

    }
}