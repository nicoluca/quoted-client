package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpService;

public class CreateQuoteDao extends CreateDao<Quote> {
    public CreateQuoteDao(Class<Quote> type, String sourceUrl) {
        super(type, sourceUrl);
    }

    public CreateQuoteDao(Class<Quote> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public Quote create(Quote quote) {
        try {
            String json = objectMapper.writeValueAsString(quote);
            String response = httpService.post(url + "/" + quote.getSource().getId() + "/quotes", json);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
