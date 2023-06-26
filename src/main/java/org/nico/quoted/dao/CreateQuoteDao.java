package org.nico.quoted.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.http.HttpService;

@Slf4j
public class CreateQuoteDao extends CreateDao<Quote> {
    public CreateQuoteDao(Class<Quote> type, String sourceUrl) {
        super(type, sourceUrl);
    }

    // Used for unit testing, to inject a mock HttpService
    public CreateQuoteDao(Class<Quote> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public Quote create(Quote quote) {
        try {
            String json = objectMapper.writeValueAsString(quote);
            log.info("Posting {} with json {} to {}", quote, json, url);
            String response = httpService.post(url + "/" + quote.getSource().getId() + "/quotes", json);
            log.info("Response: {}", response);
            return objectMapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
