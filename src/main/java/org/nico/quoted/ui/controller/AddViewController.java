package org.nico.quoted.ui.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nico.quoted.config.Logger;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.domain.Quote;

public class AddViewController extends BaseController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField urlInputField;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<Book> bookChoiceBox;

    @FXML
    private TextArea quoteInputField;

    @FXML
    private CheckBox toggleURL;

    @FXML
    void initialize() {
        checkAssertions();
        setupChoiceBox();
        urlInputField.setDisable(true);
        errorLabel.setText("");
    }

    private void setupChoiceBox() {
        bookChoiceBox.setItems(model.books);
        model.books.addListener((ListChangeListener<Book>) c -> bookChoiceBox.setItems(model.books));
    }

    private void checkAssertions() {
        assert urlInputField != null : "fx:id=\"UrlInputField\" was not injected: check your FXML file 'add-view.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'add-view.fxml'.";
        assert bookChoiceBox != null : "fx:id=\"bookChoiceBox\" was not injected: check your FXML file 'add-view.fxml'.";
        assert quoteInputField != null : "fx:id=\"quoteInputField\" was not injected: check your FXML file 'add-view.fxml'.";
        assert toggleURL != null : "fx:id=\"toggleURL\" was not injected: check your FXML file 'add-view.fxml'.";
    }

    public void toggleUrlField(ActionEvent actionEvent) {
        urlInputField.setDisable(!toggleURL.isSelected());
        bookChoiceBox.setDisable(toggleURL.isSelected());
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        if (!inputIsValid())
            displayError("Invalid input");
        else {
            displayError("");
            addQuote();
        }
    }

    private void addQuote() {
        if (toggleURL.isSelected())
            addQuoteFromUrl();
        else
            addQuoteFromBook();
    }

    private void addQuoteFromBook() {
        Book book = bookChoiceBox.getValue();
        Quote quote = new Quote(quoteInputField.getText(), book);
        model.getQuotes().add(quote);
        Logger.LOGGER.log(Logger.INFO, "Added quote from book: " + quote);
    }

    private void addQuoteFromUrl() {
        // TODO no duplicate urls
        String url = urlInputField.getText();
        SourceInterface article = new Article(null, url);
        Quote quote = new Quote(quoteInputField.getText(), article);
        model.getQuotes().add(quote);
        Logger.LOGGER.log(Logger.INFO, "Added quote from url: " + quote);
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private boolean inputIsValid() {
        String regexUrl = "^(https?|ftp)://[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        return !quoteInputField.getText().isEmpty()
                && (toggleURL.isSelected() && urlInputField.getText().matches(regexUrl)
                || !toggleURL.isSelected() && bookChoiceBox.getValue() != null);
    }
}
