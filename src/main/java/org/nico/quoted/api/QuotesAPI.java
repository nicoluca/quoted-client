package org.nico.quoted.api;

import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Quote;

import java.util.List;

public class QuotesAPI implements CRUDInterface<Quote> {

    private static QuotesAPI instance;
    private static List<Quote> quotes;

    public static QuotesAPI getInstance() {
        if (instance == null)
            instance = new QuotesAPI();
        return instance;
    }

    private QuotesAPI() {
        this.quotes = BackendConstants.defaultQuotes();
    }

    @Override
    public Quote create(Quote quote) {
        return null;
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
