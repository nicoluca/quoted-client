package org.nico.quoted.ui.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quotable;
import org.nico.quoted.domain.Quote;

import java.net.DatagramSocket;


public class SourceClientModel {

    public final ObservableList<Quotable> sources = FXCollections.observableArrayList(BackendConstants.defaultSources());
    public final ObservableList<Book> books = sources.stream()
            .filter(Book.class::isInstance)
            .map(Book.class::cast)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    public final ObservableList<Author> authors = books.stream()
            .map(Book::getAuthor)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    public final ObservableList<Quote> quotes = FXCollections.observableArrayList(BackendConstants.defaultQuotes());
    private final ObjectProperty<Quotable> selectedSource = new SimpleObjectProperty<>();

    public SourceClientModel() {
        // TODO
    }

    public ObjectProperty<Quotable> selectedSourceProperty() {
        return selectedSource;
    }

    public Quotable getSelectedSource() {
        return selectedSource.get();
    }

    public void setSelectedSource(Quotable selectedSource) {
        this.selectedSource.set(selectedSource);
    }
}
