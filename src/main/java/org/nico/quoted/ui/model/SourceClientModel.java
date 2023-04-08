package org.nico.quoted.ui.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.domain.Quote;

import java.util.List;
import java.util.stream.Collectors;


public class SourceClientModel {

    public final ObservableList<SourceInterface> sources = FXCollections.observableArrayList(BackendConstants.defaultSources());
    public final ObservableList<Book> books = sources.stream()
            .filter(Book.class::isInstance)
            .map(Book.class::cast)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    public final ObservableList<Author> authors = books.stream()
            .map(Book::getAuthor)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    public final ObservableList<Quote> quotes = FXCollections.observableArrayList(BackendConstants.defaultQuotes());
    private final ObjectProperty<SourceInterface> selectedSource = new SimpleObjectProperty<>();

    public SourceClientModel() {
        // TODO
        this.quotes.addListener((ListChangeListener<Quote>) c -> updateSources());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateBooks());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateAuthors());
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
}
