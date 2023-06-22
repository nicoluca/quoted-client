package org.nico.quoted;

import org.nico.quoted.domain.*;

import java.util.List;

public class TestConfig {
    public static final String TEST_DB_NAME = "quote_db_test";


    public static List<Author> defaultAuthors() {
        return List.of(
                new Author("J.R.R.", "Tolkien")
        );
    }

    public static List<Source> defaultBooks() {
        Author author = defaultAuthors().get(0);
        Book book = new Book("Der Herr der Ringe", author);
        book.setId(1L);
        Book book2 = new Book("Der kleine Hobbit", author);
        book2.setId(2L);
        return List.of(
                book,
                book2
        );
    }
    public static List<Article> defaultArticles() {
        Article article = new Article("Diesen Text schrieb der US-Reporter Evan Gershkovich, bevor er in Russland verhaftet wurde",
                "https://www.zeit.de/2023/15/russland-wirtschaft-energie-export-inflation");
        article.setId(3L);
        return List.of(
                article
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
