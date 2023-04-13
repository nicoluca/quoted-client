package org.nico.quoted.domain.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.nico.quoted.api.QuotesAPI;
import org.nico.quoted.api.SourcesAPI;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.config.LOGGER;
import org.nico.quoted.domain.*;

import java.util.List;
import java.util.stream.Collectors;


public class SourceClientModel {

    private final ObservableList<SourceInterface> sources;
    private final ObservableList<Book> books;
    private final ObservableList<Author> authors;
    private final ObservableList<Article> articles;
    private final ObservableList<Quote> quotes;

    private final ObjectProperty<SourceInterface> selectedSource;
    private final ObjectProperty<Quote> selectedQuote;
    private static Quote lastRandomQuote;
    private final BooleanProperty resetForm;

    public SourceClientModel() {
        // Initial read
        this.sources = FXCollections.observableArrayList(SourcesAPI.getInstance().readAll());
        this.books = FXCollections.observableArrayList(filterBooksFromSources());
        this.authors = FXCollections.observableArrayList(getAuthorsFromBooks());
        this.articles = FXCollections.observableArrayList(filterArticlesFromSources());
        this.quotes = FXCollections.observableArrayList(QuotesAPI.getInstance().readAll());

        //Selectors
        selectedSource = new SimpleObjectProperty<>();
        selectedQuote = new SimpleObjectProperty<>();
        resetForm = new SimpleBooleanProperty();

        // TODO
        this.quotes.addListener((ListChangeListener<Quote>) c -> updateSources());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateBooks());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateAuthors());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateURLs());
    }

    public ObjectProperty<Quote> selectedQuoteProperty() {
        return selectedQuote;
    }

    private List<Article> filterArticlesFromSources() {
        return sources.stream()
                .filter(Article.class::isInstance)
                .map(Article.class::cast)
                .collect(Collectors.toList());
    }

    private List<Author> getAuthorsFromBooks() {
        return books.stream()
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    private void updateURLs() {
        articles.clear();
        List<Article> newArticles = sources.stream()
                .filter(Article.class::isInstance)
                .map(Article.class::cast)
                .collect(Collectors.toList());
        articles.addAll(newArticles);
    }

    private void updateSources() {
        sources.clear();
        List<SourceInterface> newSources = quotes.stream()
                .map(Quote::getSource)
                .collect(Collectors.toList());
        sources.addAll(newSources);
    }

    private void updateAuthors() {
        authors.clear();
        List<Author> newAuthors = sources.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .map(Book::getAuthor)
                .collect(Collectors.toList());
        authors.addAll(newAuthors);
    }

    private void updateBooks() {
        books.clear();
        books.addAll(filterBooksFromSources());
    }

    private List<Book> filterBooksFromSources() {
        return sources.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .collect(Collectors.toList());
    }

    public ObservableList<SourceInterface> getSources() {
        return sources;
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public ObservableList<Author> getAuthors() {
        return authors;
    }

    public ObservableList<Quote> getQuotes() {
        return quotes;
    }

    public ObjectProperty<SourceInterface> selectedSourceProperty() {
        return selectedSource;
    }

    public void deleteQuote() {
        quotes.remove(selectedQuote.get());
    }

    public Quote getRandomQuote() {
        if (quotes.size() == 0)
            return new Quote("No quotes found", new Article("No source found", "-"));
        if (quotes.size() == 1)
            return quotes.get(0);

        Quote randomQuote;
        do {
            randomQuote = quotes.get((int) (Math.random() * quotes.size()));
        } while (lastRandomQuote != null && randomQuote.equals(lastRandomQuote));
        lastRandomQuote = randomQuote;
        return randomQuote;
    }

    public void addQuote(Quote quote) {
        quotes.add(quote);
        LOGGER.info("Added quote: " + quote.getText() + ", from source: " + quote.getSource().getOrigin());
    }

    public SourceInterface resolveArticle(String url) {
        // TODO refactor this
        Article result = this.articles.stream()
                .filter(article -> ((String) article.getOrigin()).equalsIgnoreCase(url))
                .findFirst()
                .orElse(new Article("Unknown", url));
        return result;
    }

    public void updateQuote(Quote quote) {
        quotes.remove(quote);
        quotes.add(quote);
    }

    public ObservableList<SourceInterface> getSourcesBySearch(String searchString) {
        return sources.stream()
                .filter(source -> source.getTitle().toLowerCase().contains(searchString.toLowerCase())
                        || source.getOrigin().toLowerCase().contains(searchString.toLowerCase()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> getQuotesBySearch(String searchString) {
        return quotes.stream()
                .filter(quote -> quote.getText().toLowerCase().contains(searchString.toLowerCase())
                        || quote.getSource().toString().toLowerCase().contains(searchString.toLowerCase()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> getQuotesBySource(SourceInterface source) {
        LOGGER.info("Getting quotes by source: " + source);
        return quotes.stream()
                .filter(quote -> quote.getSource().equals(source))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public void addBook(Book book) {
        sources.add(book);
        LOGGER.info("Added book: " + book);
    }

    public void resetForm() {
        this.resetForm.set(!resetForm.get());
    }

    public void registerResetListener(ChangeListener<Boolean> listener) {
        this.resetForm.addListener(listener);
    }

    public void deleteSource(SourceInterface sourceToDelete) {
        quotes.removeIf(quote -> quote.getSource().equals(sourceToDelete));
        sources.remove(sourceToDelete);
    }

    public void updateArticle(Article article) {
        // TODO refactor this
        sources.remove(article);
        sources.add(article);
    }
}
