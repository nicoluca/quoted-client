package org.nico.quoted.util;

import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Source;

import java.util.List;
import java.util.stream.Collectors;

public class ModelUtil {

    public static List<Book> filterBooksFromSources(List<Source> sources) {
        return sources.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Article> filterArticlesFromSources(List<Source> sources) {
        return sources.stream()
                .filter(Article.class::isInstance)
                .map(Article.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Author> getAuthorsFromBooks(List<Book> books) {
        return books.stream()
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }
}
