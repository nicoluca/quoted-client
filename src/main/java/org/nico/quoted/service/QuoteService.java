package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.*;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpService;

import java.util.List;
import java.util.Optional;

public class QuoteService implements Create<Quote>, ReadAll<Quote>, Update<Quote>, Delete<Quote> {

    private final CreateDao<Quote> createDao = new CreateDao<>(Quote.class, Config.QUOTES_URL);
    private final ReadAllDao<Quote> readAllDao = new ReadAllDao<>(Quote.class, Config.QUOTES_URL);
    private final UpdateDao<Quote> updateDao = new UpdateDao<>(Quote.class, Config.QUOTES_URL);
    private final DeleteDao<Quote> deleteDao = new DeleteDao<>(Quote.class, Config.QUOTES_URL);
    private final ReadDao<Quote> readRandomQuoteDao = new ReadDao<>(Quote.class, Config.QUOTES_URL);

    private final HttpService httpService = Config.HTTP_SERVICE;

    @Override
    public Quote create(Quote quote) {
        return createDao.create(quote);
    }

    @Override
    public void delete(Quote quote) {
        deleteDao.delete(quote);
    }

    @Override
    public List<Quote> readAll() {
        return readAllDao.readAll();
    }

    @Override
    public Quote update(Quote quote) {
        return updateDao.update(quote);
    }

    public Quote readRandomQuote() {
        return readRandomQuoteDao.read("random").orElseThrow();
    }

}
