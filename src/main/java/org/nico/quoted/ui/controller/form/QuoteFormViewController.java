package org.nico.quoted.ui.controller.form;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nico.quoted.domain.Article;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.Source;
import org.nico.quoted.model.EditViewModel;
import org.nico.quoted.ui.controller.MainController;
import org.nico.quoted.util.StringUtil;

public class QuoteFormViewController extends MainController {

    @FXML
    private ChoiceBox<Book> bookChoiceBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextArea quoteTextField;

    @FXML
    private CheckBox urlCheckBox;

    @FXML
    private TextField urlTextField;


    @FXML
    void initialize() {
        checkAssertions();
        displayError("");
        setupChoiceBox();

        fillQuoteForm();
        toggleUrl();
    }

    @FXML
    void onConfirmButtonClicked() {
        if (!isInputValid())
            displayError("Invalid input");
        else {
            String quoteText = quoteTextField.getText();

            Source source;
            if (urlCheckBox.isSelected())
                source = new Article(null, urlTextField.getText());
            else
                source = bookChoiceBox.getValue();

            model.updateQuote(new Quote(quoteText, source));
            model.resetForm();
            closeStage();
        }
    }

    private void closeStage() {
        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();
    }

    private boolean isInputValid() {
        if (urlCheckBox.isSelected())
            return !quoteTextField.getText().isBlank() && StringUtil.isValidURL(urlTextField.getText());
        else
            return !quoteTextField.getText().isBlank() && bookChoiceBox.getValue() != null;
    }

    private void setupChoiceBox() {
        bookChoiceBox.setItems(model.getBooks());
        model.getBooks().addListener((ListChangeListener<Book>) c -> bookChoiceBox.setItems(model.getBooks()));
    }

    private void fillQuoteForm() {
        Quote quote = EditViewModel.getQuoteToEdit();

        if (quote == null)
            throw new IllegalStateException("No quote selected");


        quoteTextField.setText(quote.getText());
        Source source = quote.getSource();

        if (source instanceof Book book) {
            bookChoiceBox.setValue(book);
            urlTextField.setText("https://...");
        } else {
            urlCheckBox.setSelected(true);
            urlTextField.setText(((Article) source).getUrl());
        }
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private void checkAssertions() {
        assert bookChoiceBox != null : "fx:id=\"bookChoiceBox\" was not injected: check your FXML file 'quote-form-view.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'quote-form-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'quote-form-view.fxml'.";
        assert quoteTextField != null : "fx:id=\"quoteTextField\" was not injected: check your FXML file 'quote-form-view.fxml'.";
        assert urlCheckBox != null : "fx:id=\"urlCheckBox\" was not injected: check your FXML file 'quote-form-view.fxml'.";
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'quote-form-view.fxml'.";
    }

    @FXML
    private void toggleUrlField() {
        toggleUrl();
    }

    private void toggleUrl() {
        urlTextField.setDisable(!urlCheckBox.isSelected());
        bookChoiceBox.setDisable(urlCheckBox.isSelected());
    }
}
