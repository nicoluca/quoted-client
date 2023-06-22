package org.nico.quoted.service;

import org.nico.quoted.dao.*;
import org.nico.quoted.domain.Book;
import org.nico.quoted.config.Config;

public class BookService implements Create<Book>, Update<Book> {

    private final CreateDao<Book> createDao = new CreateDao<>(Book.class, Config.BOOKS_URL);
    private final UpdateDao<Book> updateDao = new UpdateDao<>(Book.class, Config.BOOKS_URL);

    @Override
    public Book create(Book book) {
        return createDao.create(book);
    }

    @Override
    public Book update(Book book) {
        return updateDao.update(book);
    }

}
