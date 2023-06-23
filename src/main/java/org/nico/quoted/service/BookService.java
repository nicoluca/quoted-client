package org.nico.quoted.service;

import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.dao.*;
import org.nico.quoted.domain.Book;
import org.nico.quoted.config.Config;

@Slf4j
public class BookService implements Create<Book>, Update<Book> {

    private final CreateDao<Book> createDao = new CreateDao<>(Book.class, Config.BOOKS_URL);
    private final UpdateDao<Book> updateDao = new UpdateDao<>(Book.class, Config.BOOKS_URL);

    @Override
    public Book create(Book book) {
        log.info("Creating book: {}", book);
        return createDao.create(book);
    }

    @Override
    public Book update(Book book) {
        log.info("Updating book: {}", book);
        return updateDao.update(book);
    }

}
