package org.nico.quoted.model;

import lombok.Getter;
import org.nico.quoted.service.ArticleService;
import org.nico.quoted.service.BookService;
import org.nico.quoted.service.QuoteService;
import org.nico.quoted.service.SourceService;

// A model that contains all the services to be used in the ClientModel.
// Enables mocking of the repositories in the ClientModel for unit testing.

@Getter
public class ServiceModel {
    private final SourceService sourceService = new SourceService();
    private final BookService bookService = new BookService();
    private final ArticleService articleService = new ArticleService();
    private final QuoteService quoteService = new QuoteService();
}
