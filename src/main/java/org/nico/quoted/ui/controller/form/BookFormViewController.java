package org.nico.quoted.ui.controller.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nico.quoted.api.AuthorAPI;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.util.FileChooserUtil;
import org.nico.quoted.ui.controller.MainController;

import java.io.File;

public class BookFormViewController extends MainController {

    @FXML
    private TextField authorFirstNameTextField;

    @FXML
    private TextField authorLastNameTextField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button coverPathButton;

    @FXML
    private TextField coverPathTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    void initialize() {
        checkAssertions();
        displayError("");
        fillFields();
    }

    private void fillFields() {
        if (model.selectedSourceProperty().get() != null && model.selectedSourceProperty().get() instanceof Book book) {
            titleTextField.setText(book.getTitle());
            authorFirstNameTextField.setText(book.getAuthor().getFirstName());
            authorLastNameTextField.setText(book.getAuthor().getLastName());
            coverPathTextField.setText(book.getCoverPath());
        }
    }

    private void checkAssertions() {
        assert authorFirstNameTextField != null : "fx:id=\"authorTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert authorLastNameTextField != null : "fx:id=\"authorTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert coverPathButton != null : "fx:id=\"coverFilePathButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'book-form-view.fxml'.";
    }

    public void onConfirmButtonClicked(ActionEvent actionEvent) {
        if (checkIfBookIsValid()) {
            Author author = AuthorAPI.getInstance().getAuthor(
                    authorFirstNameTextField.getText(),
                    authorLastNameTextField.getText()
            );

            // TODO This is a tmp solution
            Book book = new Book(titleTextField.getText(), author, "12345", "coverPath");
            model.addBook(book);

            model.resetFormProperty().set(!model.resetFormProperty().get());
            closeStage();
        } else
            displayError("Invalid book data");
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private void closeStage() {
        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();
    }

    private boolean checkIfBookIsValid() {
        String regexTitle = "^[A-Za-z0-9\\s\\-',.:;()&]{2,}$"; // 2 letters minimum, allow spaces, commas, apostrophes, etc.
        String regexAuthor = "^[A-Za-z.\\s\\-']{2,}$"; // 2 letters minimum, only letters, spaces, dashes, apostrophes, periods
        return titleTextField.getText().matches(regexTitle)
                && authorFirstNameTextField.getText().matches(regexAuthor)
                && authorLastNameTextField.getText().matches(regexAuthor);
    }

    public void onCoverPathButtonClicked(ActionEvent actionEvent) {
        File file = FileChooserUtil.chooseFile((Button) actionEvent.getSource());
        if (file != null)
            coverPathTextField.setText(file.getAbsolutePath());
        // TODO Do we want to display the image somewhere?
    }
}
