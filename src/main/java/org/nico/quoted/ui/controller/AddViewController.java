package org.nico.quoted.ui.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.ui.controller.form.BookFormView;
import org.nico.quoted.util.StringUtil;

public class AddViewController extends MainController {
    @FXML
    private Button newBookButton;
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

    public void toggleUrlField(ActionEvent actionEvent) {
        urlInputField.setDisable(!toggleURL.isSelected());
        bookChoiceBox.setDisable(toggleURL.isSelected());
        newBookButton.setDisable(toggleURL.isSelected());
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        if (!inputIsValid())
            displayError("Invalid input");
        else {
            addQuote();
            displayError("Quote added!");
            resetForm();
        }
    }

    private void checkAssertions() {
        assert newBookButton != null : "fx:id=\"newBookButton\" was not injected: check your FXML file 'add-view.fxml'.";
        assert urlInputField != null : "fx:id=\"UrlInputField\" was not injected: check your FXML file 'add-view.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'add-view.fxml'.";
        assert bookChoiceBox != null : "fx:id=\"bookChoiceBox\" was not injected: check your FXML file 'add-view.fxml'.";
        assert quoteInputField != null : "fx:id=\"quoteInputField\" was not injected: check your FXML file 'add-view.fxml'.";
        assert toggleURL != null : "fx:id=\"toggleURL\" was not injected: check your FXML file 'add-view.fxml'.";
    }

    private void setupChoiceBox() {
        bookChoiceBox.setItems(model.getBooks());
        model.getBooks().addListener((ListChangeListener<Book>) c -> bookChoiceBox.setItems(model.getBooks()));
    }


    private void resetForm() {
        quoteInputField.setText("");
        urlInputField.setText("https://...");
        toggleURL.setSelected(false);
        urlInputField.setDisable(true);
        bookChoiceBox.setValue(null);
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
        model.addQuote(quote);
    }

    private void addQuoteFromUrl() {
        // TODO no duplicate urls
        String url = urlInputField.getText();
        SourceInterface article = new Article(null, url);
        Quote quote = new Quote(quoteInputField.getText(), article);
        model.addQuote(quote);
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private boolean inputIsValid() {
        return !quoteInputField.getText().isEmpty()
                && ((toggleURL.isSelected() && StringUtil.isValidURL(urlInputField.getText())
                || (!toggleURL.isSelected() && bookChoiceBox.getValue() != null)));
    }

    public void onNewBookButtonClick(ActionEvent actionEvent) {
        new BookFormView().show();
    }
}
