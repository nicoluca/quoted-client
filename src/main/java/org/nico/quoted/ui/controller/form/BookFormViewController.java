package org.nico.quoted.ui.controller.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.ui.controller.MainController;
import org.nico.quoted.util.FileChooserUtil;
import org.nico.quoted.util.StringUtil;

import java.io.File;

public class BookFormViewController extends MainController {

    @FXML
    private ImageView coverImageView;
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
    private TextField isbnTextField;

    @FXML
    void initialize() {
        checkAssertions();
        displayError("");
        fillForm();
    }

    private void checkAssertions() {
        assert authorFirstNameTextField != null : "fx:id=\"authorTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert authorLastNameTextField != null : "fx:id=\"authorTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert coverPathButton != null : "fx:id=\"coverFilePathButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert coverPathTextField != null : "fx:id=\"coverPathTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert isbnTextField != null : "fx:id=\"isbnTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
    }

    private void fillForm() {
        if (model.getSourceToEdit() != null && model.getSourceToEdit() instanceof Book book)
            fillTextFields(book);
    }

    private void fillTextFields(Book book) {
        titleTextField.setText(book.getTitle());
        authorFirstNameTextField.setText(book.getAuthor().getFirstName());
        authorLastNameTextField.setText(book.getAuthor().getLastName());
        isbnTextField.setText(book.getIsbn());
        coverPathTextField.setText(book.getCoverPath());
        if (book.getCoverPath() != null && !book.getCoverPath().isEmpty())
            coverImageView.setImage(FileChooserUtil.getImageFromPath(book.getCoverPath()));
    }

    public void onConfirmButtonClicked() {
        if (checkIfBookIsValid()) {

            Author author = new Author(
                    authorFirstNameTextField.getText(),
                    authorLastNameTextField.getText()
            );

            Book book = new Book(titleTextField.getText(), author);
            if (coverPathTextField.getText() != null && !coverPathTextField.getText().isEmpty())
                book.setCoverPath(coverPathTextField.getText());

            if (isbnTextField.getText() != null && !isbnTextField.getText().isEmpty())
                book.setIsbn(isbnTextField.getText());

            if (model.getSourceToEdit() != null && model.getSourceToEdit() instanceof Book)
                model.updateSource(book);
            else
                model.addBook(book);

            model.resetForm();
            closeStage();
        } else
            displayFormError();
    }

    private void displayFormError() {
        if (!StringUtil.isValidTitle(titleTextField.getText()))
            displayError("Title text invalid.");
        else if (!StringUtil.isValidAuthor(authorFirstNameTextField.getText()))
            displayError("Author first invalid.");
        else if (!StringUtil.isValidAuthor(authorLastNameTextField.getText()))
            displayError("Author last name invalid.");
        else if (coverPathTextField.getText() != null && !coverPathTextField.getText().isEmpty() && !StringUtil.isValidCoverPath(coverPathTextField.getText()))
            displayError("Cover path invalid.");
        else if (isbnTextField.getText() != null && !isbnTextField.getText().isEmpty() && !StringUtil.isValidISBN(isbnTextField.getText()))
            displayError("ISBN invalid.");
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private void closeStage() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private boolean checkIfBookIsValid() {
        return StringUtil.isValidTitle(titleTextField.getText())
                && StringUtil.isValidAuthor(authorFirstNameTextField.getText())
                && StringUtil.isValidAuthor(authorLastNameTextField.getText())
                && (coverPathTextField.getText() == null || coverPathTextField.getText().isEmpty() || StringUtil.isValidCoverPath(coverPathTextField.getText()))
                && (isbnTextField.getText() == null || isbnTextField.getText().isEmpty() || StringUtil.isValidISBN(isbnTextField.getText()));
    }

    public void onCoverPathButtonClicked(ActionEvent actionEvent) {
        File file = FileChooserUtil.chooseFile((Button) actionEvent.getSource());
        if (file != null) {
            coverPathTextField.setText(file.getAbsolutePath());
            coverImageView.setImage(FileChooserUtil.getImageFromPath(file.getAbsolutePath()));
        }
    }
}
