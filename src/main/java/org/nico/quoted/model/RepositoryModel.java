package org.nico.quoted.model;

import lombok.Getter;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.service.ArticleService;
import org.nico.quoted.service.BookService;
import org.nico.quoted.service.QuoteService;
import org.nico.quoted.service.SourceService;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

@Getter
public class RepositoryModel {
    private final SourceService sourceService = new SourceService();
    private final BookService bookService = new BookService();
    private final ArticleService articleService = new ArticleService();
    private final QuoteService quoteService = new QuoteService();
}
