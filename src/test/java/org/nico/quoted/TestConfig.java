package org.nico.quoted;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import java.util.List;

public class TestConfig {
    public static final String TEST_DB_NAME = "quote_db_test";
    public static final EntityManagerFactory TEST_EMF = Persistence.createEntityManagerFactory(TEST_DB_NAME);


    public static List<Author> defaultAuthors() {
        return List.of(
                new Author("J.R.R.", "Tolkien")
        );
    }

    public static List<Book> defaultBooks() {
        Author author = defaultAuthors().get(0);
        return List.of(
                new Book("Der Herr der Ringe", author),
                new Book("Der kleine Hobbit", author)
        );
    }
    public static List<Article> defaultArticles() {
        return List.of(
                new Article("Diesen Text schrieb der US-Reporter Evan Gershkovich, bevor er in Russland verhaftet wurde",
                        "https://www.zeit.de/2023/15/russland-wirtschaft-energie-export-inflation")
        );
    }

    public static List<Quote> defaultQuotes() {
        return List.of(
                new Quote("Lorem ipsum First Sample Quote", defaultBooks().get(0)),
                new Quote("Dolor sit amet Second sample quote", defaultBooks().get(1)),
                new Quote("Consectetur adipiscing elit third sample quote", defaultArticles().get(0))
        );
    }
}
