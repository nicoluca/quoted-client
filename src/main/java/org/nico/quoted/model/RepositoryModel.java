package org.nico.quoted.model;

import lombok.Getter;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.service.CrudService;
import org.nico.quoted.service.ServiceImpl;
import org.nico.quoted.serialization.ArticleDeserializer;
import org.nico.quoted.serialization.AuthorDeserializer;
import org.nico.quoted.serialization.BookDeserializer;
import org.nico.quoted.serialization.QuoteDeserializer;

// A model that contains all the repositories to be used in the ClientModel
// Enables mocking of the repositories in the ClientModel for unit testing

@Getter
public class RepositoryModel {
    private final CrudService<Author> authorRepository =
            new ServiceImpl<>(Author.class, Config.AUTHORS_URL, new AuthorDeserializer(), Config.HTTP_SERVICE);
    private final CrudService<Book> bookRepository =
            new ServiceImpl<>(Book.class, Config.BOOKS_URL, new BookDeserializer(), Config.HTTP_SERVICE);
    private final CrudService<Article> articleRepository =
            new ServiceImpl<>(Article.class, Config.ARTICLES_URL, new ArticleDeserializer(), Config.HTTP_SERVICE);
    private final CrudService<Quote> quoteRepository =
            new ServiceImpl<>(Quote.class, Config.QUOTES_URL, new QuoteDeserializer(), Config.HTTP_SERVICE);
}
