package org.nico.quoted.model;

import lombok.Getter;
import org.nico.quoted.config.DBConfig;
import org.nico.quoted.config.DBConfigExample;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.repository.CRUDRepository;
import org.nico.quoted.repository.RepositoryImplementation;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

@Getter
public class RepositoryModel {
    private final CRUDRepository<Author> authorRepository = new RepositoryImplementation<>(Author.class, DBConfig.EMF);
    private final CRUDRepository<Book> bookRepository = new RepositoryImplementation<>(Book.class, DBConfig.EMF);
    private final CRUDRepository<Article> articleRepository = new RepositoryImplementation<>(Article.class, DBConfig.EMF);
    private final CRUDRepository<Quote> quoteRepository = new RepositoryImplementation<>(Quote.class, DBConfig.EMF);
}
