package org.nico.quoted.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nico.quoted.domain.Book;

public class AddViewController extends BaseController {
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
        fillChoiceBox();
        urlInputField.setDisable(true);
    }

    private void fillChoiceBox() {
        bookChoiceBox.setItems(model.books);
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
}
