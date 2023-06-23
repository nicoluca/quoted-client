package org.nico.quoted.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.*;
import org.nico.quoted.service.ArticleService;
import org.nico.quoted.service.BookService;
import org.nico.quoted.service.QuoteService;
import org.nico.quoted.service.SourceService;

import java.sql.Timestamp;
import java.util.Random;

@Slf4j
public class ClientModel extends EditViewModel {
    
    // ############################## Setup ###########################

    private final ObservableList<Source> sources;
    private final ObservableList<Book> books;
    private final ObservableList<Author> authors;
    private final ObservableList<Article> articles;
    private final ObservableList<Quote> quotes;

    private final ObjectProperty<Source> selectedSource;
    private final ObjectProperty<Quote> selectedQuote;

    private final BooleanProperty resetForm;
    private static Quote lastRandomQuote;

    private final SourceService sourceService;
    private final BookService bookService;
    private final ArticleService articleService;
    private final QuoteService quoteService;

    public ClientModel(ServiceModel serviceModel) {

        this.sourceService = serviceModel.getSourceService();
        this.bookService = serviceModel.getBookService();
        this.quoteService = serviceModel.getQuoteService();
        this.articleService = serviceModel.getArticleService();

        // Lists
        this.sources = FXCollections.observableArrayList();
        this.books = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();
        this.articles = FXCollections.observableArrayList();
        this.quotes = FXCollections.observableArrayList();

        // Selectors
        this.selectedSource = new SimpleObjectProperty<>();
        this.selectedQuote = new SimpleObjectProperty<>();
        this.resetForm = new SimpleBooleanProperty();

        // Initialisation
        readRepositories();
        registerChangeListeners();
    }

    private void readRepositories() {
        this.sources.addAll(sourceService.readAll());

        this.books.addAll(
                this.sources.stream()
                        .filter(source -> source instanceof Book)
                        .map(source -> (Book) source)
                        .toList());
        this.articles.addAll(
                this.sources.stream()
                        .filter(source -> source instanceof Article)
                        .map(source -> (Article) source)
                        .toList());
        this.authors.addAll(
                this.books.stream()
                        .map(Book::getAuthor)
                        .distinct()
                        .toList());

        this.quotes.addAll(quoteService.readAll());
        this.quotes.forEach(quote -> quote.setSource(sources.stream()
                .filter(source -> source.getId() == quote.getSource().getId())
                .findFirst()
                .orElseThrow()));

        log.info("Retrieved data from server.");
    }

    private void registerChangeListeners() {
        this.quotes.addListener(quoteListChangeListener());
        this.sources.addListener(sourceListChangeListener());
        this.books.addListener(bookListChangeListener());
        this.authors.addListener(authorListChangeListener());
        this.articles.addListener(articleListChangeListener());
    }

    // ############################## Getters ###########################

    // Used for filtering the quotes table per source
    public void addListenerToSelectedSource(ChangeListener<Source> listener) {
        selectedSource.addListener(listener);
    }

    public void addListenerToSelectedQuote(ChangeListener<Quote> listener) {
        selectedQuote.addListener(listener);
    }

    public void bindToSelectedSource(ReadOnlyObjectProperty<Source> property) {
        selectedSource.bind(property);
    }

    public void bindToSelectedQuote(ReadOnlyObjectProperty<Quote> property) {
        selectedQuote.bind(property);
    }

    public ObservableList<Source> getSources() {
        return sources;
    }

    public Source getSourceByIndex(int index) {
        return sources.get(index);
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public ObservableList<Author> getAuthors() {
        return authors;
    }

    public ObservableList<Article> getArticles() {
        return articles;
    }

    public ObservableList<Quote> getQuotes() {
        return quotes;
    }

    // ############################## Controller API ###########################

    public void addQuote(Quote quoteToAdd) {
        quotes.add(quoteToAdd); // Sources are updating automatically once changes in quote list are registered
        log.info("Added quote: " + quoteToAdd.getText() + ", from source: " + quoteToAdd.getSource().toString());
    }

    public void deleteQuote(Quote quoteToDelete) {
        if (!quotes.contains(quoteToDelete)) {
            log.error("Quote to delete not found in quote list.");
            return;
        }

        quotes.remove(quoteToDelete); // Sources are updating automatically once changes in quote list are registered
        log.info("Removed quote: " + quoteToDelete.getText() + ", from source: " + quoteToDelete.getSource().toString());
    }

    public void deleteQuoteByIndex(int index) {
        log.info("Removing quote: " + quotes.get(index).getText() + ", from source: " + quotes.get(index).getSource().toString());
        quotes.remove(index); // Sources are updating automatically once changes in quote list are registered
    }

    public Quote getQuoteByIndex(int index) {
        return quotes.get(index);
    }

    public Quote getRandomQuote() {
        if (quotes.isEmpty())
            return new Quote("No quotes found.", new Article("No Source found.", "-"));
        if (quotes.size() == 1)
            return quotes.get(0);

        Quote randomQuote = quotes.get(new Random().nextInt(quotes.size()));
        if (randomQuote.equals(lastRandomQuote))
            return getRandomQuote();
        lastRandomQuote = randomQuote;
        return randomQuote;
    }

    public void updateQuote(Quote quote) {
        quote.setId(this.getQuoteToEdit().getId());
        quotes.set(quotes.indexOf(this.getQuoteToEdit()), quote);
        log.info("Updated quote: " + quote.getText() + ", from source: " + quote.getSource().toString());
    }

    public ObservableList<Source> searchSources(String searchString) {
        String regex = "(?i).*" + searchString + ".*";
        return sources.stream()
                .filter(source -> source.getTitle().matches(regex)
                        || source.toString().matches(regex))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> searchQuotes(String searchString) {
        String regex = "(?i).*" + searchString + ".*";
        return quotes.stream()
                .filter(quote -> quote.getText().matches(regex)
                        || quote.getSource().toString().matches(regex))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> getQuotesBySource(Source source) {
        log.info("Getting quotes by source: " + source.toString());
        return quotes.stream()
                .filter(quote -> quote.getSource().equals(source))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public void addBook(Book newBook) {
        sources.add(newBook);
        log.info("Added book: " + newBook.getTitle() + ", by author: " + newBook.getAuthor());
    }

    public void updateSource(Source source) {
        quotes.stream()
                .filter(quote -> quote.getSource().equals(this.getSourceToEdit()))
                .forEach(quote -> quote.setSource(source));

        source.setId(this.getSourceToEdit().getId());

        sources.set(sources.indexOf(this.getSourceToEdit()), source);
        log.info("Updated source: " + source);
        quotes.forEach(quote -> log.info("Updated quote: " + quote.getText() + " to new source: " + quote.getSource().toString()));
    }

    public void deleteSourceByIndex(int index) {
        log.info("Deleting source: " + sources.get(index).toString());
        this.sources.remove(index);
    }

    public void resetForm() {
        this.resetForm.set(!resetForm.get());
    }

    public void registerResetListener(ChangeListener<Boolean> listener) {
        this.resetForm.addListener(listener);
    }

    public void deleteEmptySources() {
        sources.removeIf(source ->
                getQuotesBySource(source).isEmpty());
        cleanAuthors();
    }

    // ############################## Change listeners ###########################

    private ListChangeListener<Quote> quoteListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced()) {
                    log.info("Quote list was replaced");

                    c.getAddedSubList().forEach(quote -> {
                        if (sources.contains(quote.getSource())) {
                            quote.setSource(sources.get(sources.indexOf(quote.getSource())));

                        } else
                            sources.add(quote.getSource());

                        quote.setId(quoteService.update(quote).getId());
                    });
                }

                else if (c.wasAdded()) {
                    log.info("Quote was added");
                    c.getAddedSubList().forEach(quote -> {
                        if (!sources.contains(quote.getSource())) {
                            sources.add(quote.getSource());

                            if (quote.getSource() instanceof Article article) {
                                article.setLastVisited(new Timestamp(System.currentTimeMillis()));
                                article = articleService.create(article);
                                quote.setSource(article);
                            }
                        }
                        else {
                            quote.setSource(sources.get(sources.indexOf(quote.getSource())));

                            if (quote.getSource() instanceof Article article) {
                                article.setLastVisited(new Timestamp(System.currentTimeMillis()));
                                articleService.update(article);
                            }
                        }


                        quote.setId(quoteService.create(quote).getId());
                    });
                }

                else if (c.wasRemoved()) {
                    log.info("Quote was removed");
                    c.getRemoved().forEach(quoteService::delete);
                }

            }
        };
    }

    private ListChangeListener<Source> sourceListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced()) {

                    c.getAddedSubList().forEach(source -> {
                        log.info("Updating source: " + source.toString());
                        if (source instanceof Book book)
                            updateBook(book);
                        else if (source instanceof Article article)
                            updateArticle(article);
                    });

                }

                else if (c.wasAdded())
                    c.getAddedSubList().forEach(source -> {
                        if (source instanceof Book book)
                            books.add(book);
                        else if (source instanceof Article article)
                            articles.add(article);
                    });

                else if (c.wasRemoved())
                    c.getRemoved().forEach(source -> {
                        quotes.removeIf(quote -> quote.getSource().equals(source));

                        if (source instanceof Book)
                            books.remove(source);
                        else if (source instanceof Article)
                            articles.remove(source);
                    });
            }
        };
    }

    private void updateBook(Book book) {
        int bookIndex = books.indexOf(this.getSourceToEdit());
        this.books.set(bookIndex, book);
    }

    private void updateArticle(Article article) {
        int articleIndex = articles.indexOf(this.getSourceToEdit());
        this.articles.set(articleIndex, article);
    }


    private ListChangeListener<Book> bookListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced()) {
                    c.getAddedSubList().forEach(book -> {
                        // If a matching author already exists, replace the author in the book with the existing one and delete the duplicate
                        if (this.authors.stream().anyMatch(author -> author.equals(book.getAuthor())))
                            book.setAuthor(this.authors.get(this.authors.indexOf(book.getAuthor())));
                        else
                            authors.add(book.getAuthor());

                        book.setId(bookService.update(book).getId());
                    });

                    // TODO happening on server side
                    cleanAuthors();
                }

                else if (c.wasAdded())
                    c.getAddedSubList().forEach(book -> {
                        if (this.authors.stream().anyMatch(author -> author.equals(book.getAuthor())))
                            book.setAuthor(this.authors.get(this.authors.indexOf(book.getAuthor())));
                        else
                            authors.add(book.getAuthor());

                        book.setId(bookService.create(book).getId());
                    });

                else if (c.wasRemoved()) {
                    c.getRemoved().forEach(sourceService::delete);
                    cleanAuthors();
                }
            }
        };
    }

    private void cleanAuthors() {
        this.authors.removeIf(author
                -> books.stream().noneMatch(remainingBook
                    -> remainingBook.getAuthor().equals(author)));
    }

    private ListChangeListener<? super Author> authorListChangeListener() {
        return c -> {
            while (c.next()) {
                log.info("Author list changed");
                if (c.wasReplaced()) {
                    c.getRemoved().forEach(author -> {
                        if (books.stream().noneMatch(book -> book.getAuthor().equals(author)))
                            authors.remove(author);
                    });

                    c.getAddedSubList().forEach(author -> {
                        if (!authors.contains(author))
                            authors.add(author);

                    });
                }

                // TODO
                //else if (c.wasAdded())
                //    c.getAddedSubList().forEach(authorRepository::create);

                //else if (c.wasRemoved())
                //    c.getRemoved().forEach(authorRepository::delete);
            }
        };
    }

    private ListChangeListener<Article> articleListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced())
                    c.getAddedSubList().forEach(articleService::update);

                //
                //else if (c.wasAdded())
                //    c.getAddedSubList().forEach(articleService::create);

                else if (c.wasRemoved())
                    c.getRemoved().forEach(sourceService::delete);
            }
        };
    }

}
