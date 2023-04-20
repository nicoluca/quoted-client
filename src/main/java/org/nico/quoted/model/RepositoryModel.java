package org.nico.quoted.model;

import org.nico.quoted.config.BackendConfig;
import org.nico.quoted.repository.ArticleRepository;
import org.nico.quoted.repository.AuthorRepository;
import org.nico.quoted.repository.BookRepository;
import org.nico.quoted.repository.QuoteRepository;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

public class RepositoryModel {
    private final AuthorRepository authorRepository = new AuthorRepository(BackendConfig.EMF);
    private final BookRepository bookRepository = new BookRepository(BackendConfig.EMF);
    private final ArticleRepository articleRepository = new ArticleRepository(BackendConfig.EMF);
    private final QuoteRepository quoteRepository = new QuoteRepository(BackendConfig.EMF);

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    public QuoteRepository getQuoteRepository() {
        return quoteRepository;
    }
}
