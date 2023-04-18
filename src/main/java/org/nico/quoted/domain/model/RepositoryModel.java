package org.nico.quoted.domain.model;

import org.nico.quoted.repository.ArticleRepository;
import org.nico.quoted.repository.AuthorRepository;
import org.nico.quoted.repository.BookRepository;
import org.nico.quoted.repository.QuoteRepository;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

public class RepositoryModel {
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final BookRepository bookRepository = new BookRepository();
    private final ArticleRepository articleRepository = new ArticleRepository();
    private final QuoteRepository quoteRepository = new QuoteRepository();

    protected AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    protected BookRepository getBookRepository() {
        return bookRepository;
    }

    protected ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    protected QuoteRepository getQuoteRepository() {
        return quoteRepository;
    }
}
