package org.nico.quoted.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.repository.ArticleRepository;
import org.nico.quoted.repository.AuthorRepository;
import org.nico.quoted.repository.BookRepository;
import org.nico.quoted.repository.QuoteRepository;
import org.nico.quoted.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClientModel {
    
    // ############################## Setup ###########################

    private final ObservableList<Source> sources;
    private final ObservableList<Book> books;
    private final ObservableList<Author> authors;
    private final ObservableList<Article> articles;
    private final ObservableList<Quote> quotes;

    private final ObjectProperty<Source> selectedSource;
    private final ObjectProperty<Quote> selectedQuote;
    private static Quote lastRandomQuote;
    private final BooleanProperty resetForm;

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ArticleRepository articleRepository;
    private final QuoteRepository quoteRepository;

    public ClientModel(RepositoryModel repositoryModel) {

        this.authorRepository = repositoryModel.getAuthorRepository();
        this.bookRepository = repositoryModel.getBookRepository();
        this.articleRepository = repositoryModel.getArticleRepository();
        this.quoteRepository = repositoryModel.getQuoteRepository();

        // Lists
        this.sources = FXCollections.observableArrayList();
        this.books = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();
        this.articles = FXCollections.observableArrayList();
        this.quotes = FXCollections.observableArrayList();

        readRepositories();

        // Selectors
        selectedSource = new SimpleObjectProperty<>();
        selectedQuote = new SimpleObjectProperty<>();
        resetForm = new SimpleBooleanProperty();

        // Change Listeners
        // TODO Register the change listeners to update the API/DB - Question: Just adding via quote or source?
        this.quotes.addListener(quoteListChangeListener());
        this.sources.addListener(sourceListChangeListener());
        this.books.addListener(bookListChangeListener());
        this.authors.addListener(authorListChangeListener());
        this.articles.addListener(articleListChangeListener());
    }

    private void readRepositories() {
        // TODO Filter or read directly?
        this.sources.addAll(bookRepository.readAll());
        this.sources.addAll(articleRepository.readAll());

        this.books.addAll(filterBooksFromSources());
        this.authors.addAll(getAuthorsFromBooks());
        this.articles.addAll(filterArticlesFromSources());

        this.quotes.addAll(quoteRepository.readAll());
        log.info("Repositories read into model.");
    }

    // ############################## Getters ###########################

    public ObjectProperty<Source> selectedSourceProperty() {
        return selectedSource; // TODO SETTING the selected source will throw an InvocationTargetException as the source is bound. Either remove the binding, or restrict access to the property.
    }

    public ObjectProperty<Quote> selectedQuoteProperty() {
        return selectedQuote;
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

    public void deleteQuoteByIndex(int index) {
        log.info("Removing quote: " + quotes.get(index).getText() + ", from source: " + quotes.get(index).getSource().toString());
        quotes.remove(index); // Sources are updating automatically once changes in quote list are registered
    }

    public Quote getQuoteByIndex(int index) {
        return quotes.get(index);
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
        quote.setId(EditViewModel.getQuoteToEdit().getId());
        quotes.set(quotes.indexOf(EditViewModel.getQuoteToEdit()), quote);
        log.info("Updated quote: " + quote.getText() + ", from source: " + quote.getSource().toString());
    }

    public ObservableList<Source> searchSources(String searchString) {
        return sources.stream()
                .filter(source -> source.getTitle().toLowerCase().contains(searchString.toLowerCase())
                        || source.toString().toLowerCase().contains(searchString.toLowerCase()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public ObservableList<Quote> searchQuotes(String searchString) {
        return quotes.stream()
                .filter(quote -> quote.getText().toLowerCase().contains(searchString.toLowerCase())
                        || quote.getSource().toString().toLowerCase().contains(searchString.toLowerCase()))
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
        // TODO How about storing the previous source as a defensive copy?
        quotes.stream()
                .filter(quote -> quote.getSource().equals(EditViewModel.getSourceToEdit()))
                .forEach(quote -> quote.setSource(source));

        source.setId(EditViewModel.getSourceToEdit().getId());

        sources.set(sources.indexOf(EditViewModel.getSourceToEdit()), source);
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

                        quoteRepository.update(quote);
                    });

                    cleanSources();
                }

                else if (c.wasAdded()) {
                    log.info("Quote was added");
                    c.getAddedSubList().forEach(quote -> {
                        if (!sources.contains(quote.getSource()))
                            sources.add(quote.getSource());
                        else
                            quote.setSource(sources.get(sources.indexOf(quote.getSource())));

                        quoteRepository.create(quote);
                    });
                }

                else if (c.wasRemoved()) {
                    log.info("Quote was removed");
                    c.getRemoved().forEach(quote -> {
                        quoteRepository.delete(quote);
                        cleanSources();
                    });
                }

            }
        };
    }

    private void cleanSources() {
        sources.removeIf(source ->
                getQuotesBySource(source).isEmpty());
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
        int bookIndex = books.indexOf(EditViewModel.getSourceToEdit());
        this.books.set(bookIndex, book);
    }

    private void updateArticle(Article article) {
        int articleIndex = articles.indexOf(EditViewModel.getSourceToEdit());
        this.articles.set(articleIndex, article);
    }


    private ListChangeListener<Book> bookListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced()) {
                    // TODO check if this is covered by unit test
                    c.getAddedSubList().forEach(book -> {
                        // If a matching author already exists, replace the author in the book with the existing one and delete the duplicate
                        if (this.authors.stream().anyMatch(author -> author.equals(book.getAuthor())))
                            book.setAuthor(this.authors.get(this.authors.indexOf(book.getAuthor())));
                        else
                            authors.add(book.getAuthor());

                        bookRepository.update(book);
                    });

                    cleanAuthors();
                }

                else if (c.wasAdded())
                    c.getAddedSubList().forEach(book -> {
                        if (this.authors.stream().anyMatch(author -> author.equals(book.getAuthor())))
                            book.setAuthor(this.authors.get(this.authors.indexOf(book.getAuthor())));
                        else
                            authors.add(book.getAuthor());

                        bookRepository.create(book);
                    });

                else if (c.wasRemoved()) {
                    c.getRemoved().forEach(bookRepository::delete);
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

                        authorRepository.update(author);
                    });
                }

                else if (c.wasAdded())
                    c.getAddedSubList().forEach(authorRepository::create);

                else if (c.wasRemoved())
                    c.getRemoved().forEach(authorRepository::delete);
            }
        };
    }

    private ListChangeListener<Article> articleListChangeListener() {
        return c -> {
            while (c.next()) {
                if (c.wasReplaced())
                    c.getAddedSubList().forEach(articleRepository::update);

                else if (c.wasAdded())
                    c.getAddedSubList().forEach(articleRepository::create);

                else if (c.wasRemoved())
                    c.getRemoved().forEach(articleRepository::delete);
            }
        };
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

}
