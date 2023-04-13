package org.nico.quoted.domain.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.nico.quoted.api.QuotesAPI;
import org.nico.quoted.api.SourcesAPI;
import org.nico.quoted.config.LOGGER;
import org.nico.quoted.domain.*;

import java.util.List;
import java.util.stream.Collectors;


public class SourceClientModel {

    // ############################## Setup ###########################

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

        // Selectors
        selectedSource = new SimpleObjectProperty<>();
        selectedQuote = new SimpleObjectProperty<>();
        resetForm = new SimpleBooleanProperty();

        // Change Listeners
        this.quotes.addListener((ListChangeListener<Quote>) c -> updateSources());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateBooks());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateAuthors());
        this.sources.addListener((ListChangeListener<SourceInterface>) c -> updateURLs());

        // TODO Register the change listeners to update the API
    }

    // ############################## Getters ###########################

    public ObjectProperty<SourceInterface> selectedSourceProperty() {
        return selectedSource;
    }

    public ObjectProperty<Quote> selectedQuoteProperty() {
        return selectedQuote;
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

    // ############################## Controller API ###########################

    public void addQuote(Quote quoteToAdd) {
        quotes.add(quoteToAdd); // Sources are updating automatically once changes in quote list are registered
        LOGGER.info("Added quote: " + quoteToAdd.getText() + ", from source: " + quoteToAdd.getSource().getOrigin());
    }

    public void deleteQuote(Quote quoteToDelete) {
        quotes.remove(quoteToDelete); // Sources are updating automatically once changes in quote list are registered
        LOGGER.info("Removed quote: " + quoteToDelete.getText() + ", from source: " + quoteToDelete.getSource().getOrigin());
    }

    public Quote getRandomQuote() {
        if (quotes.size() == 0)
            return new Quote("No quotes found", new Article("No source found", "-"));
        if (quotes.size() == 1)
            return quotes.get(0);

        Quote randomQuote;
        do {
            randomQuote = quotes.get((int) (Math.random() * quotes.size()));
        } while (randomQuote.equals(lastRandomQuote));
        lastRandomQuote = randomQuote;
        return randomQuote;
    }


    public void updateQuote(Quote quote) {
        quotes.stream().filter(quoteToUpdate -> quoteToUpdate.equals(quote)).findFirst().ifPresent(quote1 -> {
            quote1.setText(quote.getText());
            quote1.setSource(quote.getSource());
        });
    }

    public ObservableList<SourceInterface> searchSources(String searchString) {
        return sources.stream()
                .filter(source -> source.getTitle().toLowerCase().contains(searchString.toLowerCase())
                        || source.getOrigin().toLowerCase().contains(searchString.toLowerCase()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> searchQuotes(String searchString) {
        return quotes.stream()
                .filter(quote -> quote.getText().toLowerCase().contains(searchString.toLowerCase())
                        || quote.getSource().toString().toLowerCase().contains(searchString.toLowerCase()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> getQuotesBySource(SourceInterface source) {
        LOGGER.info("Getting quotes by source: " + source.getOrigin());
        return quotes.stream()
                .filter(quote -> quote.getSource().equals(source))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public void addOrUpdateBook(Book newBook) {
        if (selectedSource.get() == null)
            sources.add(newBook);
        else
            updateBook(newBook);
    }

    private void updateBook(Book newBook) {
        int index = sources.indexOf(selectedSource.get());
        if (index == -1)
            throw new IllegalStateException("Book not found in sources.");

        // TODO with DB
        // book.setId(selectedSource.get().getId());

        sources.set(index, newBook);
    }

    public void updateArticle(Article newArticle) {
        // Get index of article to update
        // TODO This selection is error-prone: what if user selects some other source in background?
        int index = sources.indexOf(selectedSource.get());
        if (index == -1)
            throw new IllegalStateException("Article not found in sources.");

        // TODO with DB
        // newArticle.setId(selectedSource.get().getId());

         sources.set(index, newArticle);
    }

    public void deleteSource(SourceInterface sourceToDelete) {
        quotes.removeIf(quote -> quote.getSource().equals(sourceToDelete));
        sources.remove(sourceToDelete);
    }

    public void resetForm() {
        this.resetForm.set(!resetForm.get());
    }

    public void registerResetListener(ChangeListener<Boolean> listener) {
        this.resetForm.addListener(listener);
    }


    // ############################## Filters for change listeners ###########################

    private void updateURLs() {
        // TODO is this efficient? Same for the following methods
        articles.clear();
        articles.addAll(filterArticlesFromSources());
    }

    private void updateSources() {
        sources.clear();
        sources.addAll(getSourcesFromQuotes());
    }

    private void updateAuthors() {
        authors.clear();
        authors.addAll(getAuthorsFromBooks());
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

    private List<Article> filterArticlesFromSources() {
        return sources.stream()
                .filter(Article.class::isInstance)
                .map(Article.class::cast)
                .collect(Collectors.toList());
    }

    private List<Author> getAuthorsFromBooks() {
        return books.stream()
                .map(Book::getAuthor)
                .distinct() // TODO check if this is necessary
                .collect(Collectors.toList());
    }

    private List<SourceInterface> getSourcesFromQuotes() {
        return quotes.stream()
                .map(Quote::getSource)
                .collect(Collectors.toList());
    }

}
