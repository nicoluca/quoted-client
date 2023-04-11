package org.nico.quoted.ui.controller.form;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.SourceInterface;
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
    void onConfirmButtonClicked(ActionEvent event) {
        if (!inputIsValid())
            displayError("Invalid input");
        else {
            Quote quote = model.selectedQuoteProperty().get();
            quote.setText(quoteTextField.getText());

            if (urlCheckBox.isSelected()) {
                quote.setSource(model.resolveArticle(urlTextField.getText()));
            } else {
                quote.setSource(bookChoiceBox.getValue());
            }

            model.updateQuote(quote);
            model.resetFormProperty().set(!model.resetFormProperty().get());
            closeStage();
        }
    }

    private void closeStage() {
        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();
    }

    private boolean inputIsValid() {
        if (urlCheckBox.isSelected())
            return !quoteTextField.getText().isBlank() && StringUtil.isValidURL(urlTextField.getText());
        else
            return !quoteTextField.getText().isBlank() && bookChoiceBox.getValue() != null;
    }

    @FXML
    void initialize() {
        checkAssertions();
        displayError("");
        setupChoiceBox();
        urlCheckBox.setSelected(false);

        fillQuoteForm();
    }

    private void setupChoiceBox() {
        bookChoiceBox.setItems(model.getBooks());
        model.getBooks().addListener((ListChangeListener<Book>) c -> bookChoiceBox.setItems(model.getBooks()));
    }

    private void fillQuoteForm() {
        Quote quote = model.selectedQuoteProperty().get();

        if (quote == null)
            throw new IllegalStateException("No quote selected");

        quoteTextField.setText(quote.getText());
        SourceInterface source = quote.getSource();

        if (source instanceof Book) {
            bookChoiceBox.setValue((Book) source);
        } else {
            urlCheckBox.setSelected(true);
            urlTextField.setText(source.getOrigin());
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

    public void toggleUrlField(ActionEvent actionEvent) {
        urlTextField.setDisable(!urlCheckBox.isSelected());
        bookChoiceBox.setDisable(urlCheckBox.isSelected());
    }
}
