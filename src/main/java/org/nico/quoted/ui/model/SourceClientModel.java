package org.nico.quoted.ui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quotable;
import org.nico.quoted.domain.Quote;

import java.util.List;

public class SourceClientModel {
    private static List<Quotable> defaultSources() {
        return BackendConstants.defaultSources();
    }

    private static List<Quote> defaultQuotes() {
        return BackendConstants.defaultQuotes();
    }

    public final ObservableList<Quotable> sources = FXCollections.observableArrayList(BackendConstants.defaultSources());
    public final ObservableList<Book> books = sources.stream()
            .filter(Book.class::isInstance)
            .map(Book.class::cast)
            .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    public final ObservableList<Quote> quotes = FXCollections.observableArrayList(BackendConstants.defaultQuotes());

    public SourceClientModel() {
        // TODO
    }
}
