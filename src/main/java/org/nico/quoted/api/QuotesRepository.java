package org.nico.quoted.api;

import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Quote;

import java.util.List;

public class QuotesRepository implements CRUDRepositoryJPA<Quote> {

    private static QuotesRepository instance;
    private static List<Quote> quotes;

    public static QuotesRepository getInstance() {
        if (instance == null)
            instance = new QuotesRepository();
        return instance;
    }

    private QuotesRepository() {
        this.quotes = BackendConstants.defaultQuotes();
    }

    @Override
    public void create(Quote quote) {
        return;
    }

    @Override
    public Quote readById(long id) {
        return null;
    }

    @Override
    public List<Quote> readAll() {
        return quotes;
    }

    @Override
    public void update(Quote quote) {

    }

    @Override
    public void delete(Quote quote) {

    }
}
