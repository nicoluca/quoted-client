package org.nico.quoted.service;

import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.config.Config;
import org.nico.quoted.dao.*;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpService;

import java.util.List;

@Slf4j
public class QuoteService implements Create<Quote>, ReadAll<Quote>, Update<Quote>, Delete<Quote> {

    private final CreateDao<Quote> createDao = new CreateQuoteDao(Quote.class, Config.SOURCE_URL);
    private final ReadAllDao<Quote> readAllDao = new ReadAllDao<>(Quote.class, Config.QUOTES_URL);
    private final UpdateDao<Quote> updateDao = new UpdateDao<>(Quote.class, Config.QUOTES_URL);
    private final DeleteDao<Quote> deleteDao = new DeleteDao<>(Quote.class, Config.QUOTES_URL);
    private final ReadDao<Quote> readRandomQuoteDao = new ReadDao<>(Quote.class, Config.QUOTES_URL);

    private final HttpService httpService = Config.HTTP_SERVICE;

    @Override
    public Quote create(Quote quote) {
        log.info("Creating quote: {}", quote);
        return createDao.create(quote);
    }

    @Override
    public void delete(Quote quote) {
        log.info("Deleting quote: {}", quote);
        deleteDao.delete(quote);
    }

    @Override
    public List<Quote> readAll() {
        log.info("Reading all quotes");
        return readAllDao.readAll();
    }

    @Override
    public Quote update(Quote quote) {
        log.info("Updating quote: {}", quote);
        delete(quote);
        return create(quote);
    }

}
