package org.nico.quoted.model;

import lombok.Getter;
import org.nico.quoted.config.DBConfig;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.repository.CrudService;
import org.nico.quoted.repository.ServiceImpl;
import org.nico.quoted.serialization.ArticleDeserializer;
import org.nico.quoted.serialization.AuthorDeserializer;
import org.nico.quoted.serialization.BookDeserializer;
import org.nico.quoted.serialization.QuoteDeserializer;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

@Getter
public class RepositoryModel {
    private final CrudService<Author> authorRepository =
            new ServiceImpl<>(Author.class, DBConfig.AUTHORS_URL, new AuthorDeserializer(), DBConfig.HTTP_SERVICE);
    private final CrudService<Book> bookRepository =
            new ServiceImpl<>(Book.class, DBConfig.BOOKS_URL, new BookDeserializer(), DBConfig.HTTP_SERVICE);
    private final CrudService<Article> articleRepository =
            new ServiceImpl<>(Article.class, DBConfig.ARTICLE_URL, new ArticleDeserializer(), DBConfig.HTTP_SERVICE);
    private final CrudService<Quote> quoteRepository =
            new ServiceImpl<>(Quote.class, DBConfig.QUOTES_URL, new QuoteDeserializer(), DBConfig.HTTP_SERVICE);
}
