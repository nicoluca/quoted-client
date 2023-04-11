package org.nico.quoted.ui.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.config.LOGGER;
import org.nico.quoted.domain.*;

import java.util.List;
import java.util.stream.Collectors;


public class SourceClientModel {

    private final ObservableList<SourceInterface> sources = FXCollections.observableArrayList(BackendConstants.defaultSources());
    private final ObservableList<Book> books = sources.stream()
            .filter(Book.class::isInstance)
            .map(Book.class::cast)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    private final ObservableList<Author> authors = books.stream()
            .map(Book::getAuthor)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    private final ObservableList<Article> articles = sources.stream()
            .filter(Article.class::isInstance)
            .map(Article.class::cast)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    private final ObservableList<Quote> quotes = FXCollections.observableArrayList(BackendConstants.defaultQuotes());
    private final ObjectProperty<SourceInterface> selectedSource = new SimpleObjectProperty<>();
    private final ObjectProperty<Quote> selectedQuote = new SimpleObjectProperty<>();
    private static Quote lastRandomQuote = null;
    private final BooleanProperty resetForm = new SimpleBooleanProperty();

    public void setSelectedQuote(Quote selectedQuote) {

        this.selectedQuote.set(selectedQuote);
    }

    public ObjectProperty<Quote> selectedQuoteProperty() {
        return selectedQuote;
    }

    public SourceClientModel() {
        // TODO
        this.quotes.addListener((ListChangeListener<Quote>) c -> updateSources());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateBooks());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateAuthors());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateURLs());
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
        List<Book> newBooks = sources.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .collect(Collectors.toList());
        books.addAll(newBooks);
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

    public SourceInterface getSelectedSource() {
        return selectedSource.get();
    }

    public void setSelectedSource(SourceInterface selectedSource) {
        this.selectedSource.set(selectedSource);
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
                        || quote.getSource().getTitle().toLowerCase().contains(searchString.toLowerCase()))
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

    public BooleanProperty resetFormProperty() {
        return resetForm;
    }
}
