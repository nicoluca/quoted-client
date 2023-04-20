package org.nico.quoted.domain.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.*;
import org.nico.quoted.model.ClientModel;
import org.nico.quoted.model.EditViewModel;
import org.nico.quoted.model.RepositoryModel;
import org.nico.quoted.repository.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientModelTest {
    private ClientModel model;
    // TODO Increase test coverage beyond basic functionality by adding quotes with same source, author, book, etc., and testing for correct number of books etc. returned

    @Mock
    private RepositoryModel repositoryModel = mock(RepositoryModel.class);
    @Mock
    private AuthorRepository authorRepository = mock(AuthorRepository.class);
    @Mock
    private BookRepository bookRepository = mock(BookRepository.class);
    @Mock
    private QuoteRepository quoteRepository = mock(QuoteRepository.class);
    @Mock
    private ArticleRepository articleRepository= mock(ArticleRepository.class);

    @BeforeAll
    static void init() {
        mockStatic(EditViewModel.class);
    }

    @BeforeEach
    void setUp() {
        when(repositoryModel.getAuthorRepository()).thenReturn(authorRepository);
        when(repositoryModel.getBookRepository()).thenReturn(bookRepository);
        when(repositoryModel.getQuoteRepository()).thenReturn(quoteRepository);
        when(repositoryModel.getArticleRepository()).thenReturn(articleRepository);

        // The test model has 2 books by 1 author and 1 article; 3 quotes each from a different source.
        when(bookRepository.readAll()).thenReturn(BackendConstants.defaultBooks());
        when(articleRepository.readAll()).thenReturn(BackendConstants.defaultArticles());
        when(quoteRepository.readAll()).thenReturn(BackendConstants.defaultQuotes());
        when(authorRepository.readAll()).thenReturn(BackendConstants.defaultAuthors());

        model = new ClientModel(repositoryModel);
    }


    // ########## BASE TESTS ##########

    @Test
    @DisplayName("Test if the model returns the correct number of sources")
    void getSources() {
        assertEquals(3, numberOfSources());
    }

    @Test
    @DisplayName("Test if the model returns sources by index correctly")
    void getSourceByIndex() {
        assert firstBook() != null;
        assertEquals(BackendConstants.defaultBooks().get(0), firstBook());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of books")
    void getBooks() {
        assertEquals(2, numberOfBooks());
    }

    @Test
    @DisplayName("Test if the model returns the correct authors")
    void getAuthors() {
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes")
    void getQuotes() {
        assertEquals(3, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after adding a new quote")
    void addQuote() {
        Quote quote = new Quote("Test", firstBook());
        Mockito.doNothing().when(quoteRepository).create(quote);
        model.addQuote(quote);
        assertEquals(4, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct number of quotes after deleting a quote")
    void deleteQuoteByIndex() {
        Mockito.doNothing().when(quoteRepository).delete(firstQuote());
        model.deleteQuoteByIndex(0);
        assertEquals(2, numberOfQuotes());
    }

    @Test
    @DisplayName("Test if the model returns the correct quote by index")
    void getQuoteByIndex() {
        assertNotNull(firstQuote());
        assertEquals(BackendConstants.defaultQuotes().get(0), firstQuote());
    }

    @Test
    @DisplayName("Test returning a random quote")
    void getRandomQuote() {
        assertNotNull(model.getRandomQuote());
    }

    @Test
    @DisplayName("Test updating a quote")
    void updateQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(firstQuote());
        when(EditViewModel.getSourceToEdit()).thenReturn(firstSource());
        Quote quote = new Quote("Test", firstSource());
        model.updateQuote(quote);

        assertEquals("Test", firstQuote().getText());
        assertEquals(3, numberOfQuotes());
    }

    @Test
    @DisplayName("Test searching sources by text")
    void searchSources() {
        assertEquals(2, model.searchSources("Tolkien").size());
    }

    @Test
    @DisplayName("Test searching quotes by text")
    void searchQuotes() {
        assertEquals(1, model.searchQuotes("Lorem").size());
        assertEquals(2, model.searchQuotes("Tolkien").size());
    }

    @Test
    @DisplayName("Test filtering quotes by source")
    void getQuotesBySource() {
        assertEquals(1, model.getQuotesBySource(firstSource()).size());
        Quote quote = new Quote("Test", firstSource());
        model.addQuote(quote);
        assertEquals(2, model.getQuotesBySource(firstSource()).size());
    }

    @Test
    @DisplayName("Test adding a new book")
    void addBook() {
        Book book = new Book("Test", new Author("Test", "Test"), "Test");
        Mockito.doNothing().when(bookRepository).create(book);
        model.addBook(book);
        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
    }

    @Test
    @DisplayName("Test updating sources")
    void updateSource() {
        updateBook();
        updateArticle();
    }

    private void updateBook() {
        when(EditViewModel.getSourceToEdit()).thenReturn(firstSource());

        assert firstSource() instanceof Book;
        Book bookToUpdate = firstBook();
        Book updatingBook = new Book("Test", bookToUpdate.getAuthor(), "Test");
        Mockito.doNothing().when(bookRepository).update(bookToUpdate);


        model.updateSource(updatingBook);
        assertEquals("Test", firstSource().getTitle());
        assertEquals(3, numberOfSources());
        assertEquals(2, numberOfBooks());
    }

    private void updateArticle() {
        when(EditViewModel.getSourceToEdit()).thenReturn(firstArticle());

        assert model.getSourceByIndex(2).equals(firstArticle());
        Article articleToUpdate = firstArticle();
        Article updatingArticle = new Article("Test", "https://www.test.com");
        Mockito.doNothing().when(articleRepository).update(articleToUpdate);

        model.updateSource(updatingArticle);
        assertEquals("Test", firstArticle().getTitle());
        assertEquals("https://www.test.com", firstArticle().getUrl());
        assertEquals(3, numberOfSources());
        assertEquals(1, numberOfArticles());
    }

    @Test
    @DisplayName("Test deleting sources")
    void deleteSourceByIndex() {
        model.deleteSourceByIndex(0);
        assertEquals(2, numberOfSources());
        assertEquals(1, numberOfBooks());
        assertEquals(1, numberOfArticles());
    }

    // ######### EDGE CASES #########
    @Test
    @DisplayName("Test: Change the book of a quote by changing its title")
    void changeBookTitleOfQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(firstQuote());
        Quote quote = firstQuote();
        Book book = (Book) quote.getSource();

        book.setTitle("Test");
        model.updateQuote(quote);

        assertEquals("Test", firstQuote().getSource().getTitle());
    }

    @Test
    @DisplayName("Test: Change the book of a quote by changing its author to a new author")
    void changeAuthorOfBookOfQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(firstQuote());
        Quote quote = firstQuote();
        Book book = (Book) quote.getSource();

        book.setAuthor(new Author("Test", "Test"));
        model.updateQuote(quote);

        assertTrue(model.getAuthors().stream().anyMatch(author -> author.equals(new Author("Test", "Test"))));
        assertEquals(2, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Change author of existing book to new author")
    void changeAuthorOfExistingBook() {
        when(EditViewModel.getSourceToEdit()).thenReturn(firstSource());
        firstBook().setAuthor(new Author("Max", "Frisch"));
        model.updateSource(firstBook());

        assertEquals(2, numberOfAuthors());
        assertTrue(model.getAuthors().stream().anyMatch(author -> author.equals(new Author("Max", "Frisch"))));
    }

    @Test
    @DisplayName("Test: Add a book with an existing author (object)")
    void addBookWithExistingAuthor() {
        Author author = firstBook().getAuthor();
        Book book = new Book("Test", author, "Test");

        Mockito.doNothing().when(bookRepository).create(book);
        model.addBook(book);

        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Add a book with an existing author (String)")
    void addBookWithExistingAuthorString() {
        Author author = new Author("J.R.R.", "Tolkien");
        Book book = new Book("Test", author, "Test");

        Mockito.doNothing().when(bookRepository).create(book);
        model.addBook(book);

        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

    @Test
    @DisplayName("Test: Add a quote with a new book with an existing author (String)")
    void addQuoteWithBookWithExistingAuthor() {
        Author author = new Author("J.R.R.", "Tolkien");
        Book book = new Book("Test", author, "Test");
        Quote quote = new Quote("Test", book);

        Mockito.doNothing().when(bookRepository).create(book);
        Mockito.doNothing().when(quoteRepository).create(quote);

        model.addQuote(quote);
        assertEquals(4, numberOfQuotes());
        assertEquals(4, numberOfSources());
        assertEquals(3, numberOfBooks());
        assertEquals(1, numberOfAuthors());
    }

//    @Test
//    @DisplayName("Test: Change the book of a quote by changing its author to an existing author")
//    // Disabled as no use case for user
//    void changeAuthorOfBookOfQuoteToExistingAuthor() {
//        when(EditViewModel.getQuoteToEdit()).thenReturn(firstQuote());
//        Quote quote = firstQuote();
//        Book book = (Book) quote.getSource();
//        Author existingAuthor = new Author("J.R.R.", "Tolkien");
//        Author newAuthor = new Author("Test", "Test");
//
//        book.setAuthor(newAuthor);
//        model.updateQuote(quote);
//
//        assertEquals("Test", ((Book) firstQuote().getSource()).getAuthor().getFirstName());
//        assertEquals(2, numberOfAuthors());
//
//        book.setAuthor(existingAuthor);
//        model.updateQuote(quote);
//
//        assertEquals("J.R.R.", ((Book) firstQuote().getSource()).getAuthor().getFirstName());
//        assertEquals(1, numberOfAuthors());
//    }

    @Test
    @DisplayName("Test: Change the article of a quote by changing its url")
    void changeArticleUrlOfQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(model.getQuoteByIndex(2));
        Quote quote = model.getQuoteByIndex(2);
        Article article = (Article) quote.getSource();

        article.setUrl("https://www.test.com");
        model.updateQuote(quote);

        assertEquals("https://www.test.com", ((Article) model.getQuoteByIndex(2).getSource()).getUrl());
        assertEquals(3, model.getSources().size());
        assertEquals(1, model.getArticles().size());
    }

    @Test
    @DisplayName("Test: Change the article of a quote by replacing it with a new article")
    void changeArticleOfQuote() {
        when(EditViewModel.getQuoteToEdit()).thenReturn(thirdQuote());
        Quote quote = thirdQuote();
        Article newArticle = new Article("Test", "https://www.test.com");
        quote.setSource(newArticle);
        model.updateQuote(quote);

        assertEquals("https://www.test.com", ((Article) thirdQuote().getSource()).getUrl());
        assertEquals(3, numberOfSources());
        assertEquals(1, numberOfArticles());
    }

    @Test
    @DisplayName("Test: Delete a book with a quote")
    void deleteBookWithQuote() {
        model.deleteSourceByIndex(0);
        assertEquals(2, numberOfSources());
        assertEquals(2, numberOfQuotes());
        assertEquals(1, numberOfBooks());
    }

    @Test
    @DisplayName("Test: Delete an article with a quote")
    void deleteArticleWithQuote() {
        model.deleteSourceByIndex(2);
        assertEquals(2, numberOfSources());
        assertEquals(2, numberOfQuotes());
        assertEquals(0, numberOfArticles());
    }

    @Test
    @DisplayName("Change source of quote from book to article")
    void changeSourceOfQuoteFromBookToArticle() {
        // TODO EditViewModel does not need to be mocked, just set the value
        when(EditViewModel.getQuoteToEdit()).thenReturn(firstQuote());
        when(EditViewModel.getSourceToEdit()).thenReturn(firstQuote().getSource());
        Quote quote = firstQuote();
        assert quote.getSource() instanceof Book;
        Article article = new Article("Test", "https://www.test.com");
        quote.setSource(article);

        model.updateQuote(quote);

        assertTrue(quote.getSource() instanceof Article);
        assertEquals(3, numberOfSources());
        assertEquals(2, numberOfArticles());
        assertEquals(1, numberOfBooks());
    }

    Quote firstQuote() {
        return model.getQuoteByIndex(0);
    }

    Quote thirdQuote() {
        return model.getQuoteByIndex(2);
    }

    Source firstSource() {
        return model.getSourceByIndex(0);
    }

    Book firstBook() {
        return (Book) model.getSourceByIndex(0);
    }

    Article firstArticle() {
        return (Article) model.getSourceByIndex(2);
    }

    int numberOfSources() {
        return model.getSources().size();
    }

    int numberOfBooks() {
        return model.getBooks().size();
    }

    int numberOfArticles() {
        return model.getArticles().size();
    }

    int numberOfAuthors() {
        return model.getAuthors().size();
    }

    int numberOfQuotes() {
        return model.getQuotes().size();
    }
}

