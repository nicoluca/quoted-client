package org.nico.quoted.ui.controller.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nico.quoted.domain.Article;
import org.nico.quoted.model.EditViewModel;
import org.nico.quoted.ui.controller.MainController;
import org.nico.quoted.util.StringUtil;

public class ArticleFormViewController extends MainController {

    @FXML
    private Button confirmButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField urlTextField;

    @FXML
    void initialize() {
        checkAssertions();
        displayError("");
        fillForm();
    }

    @FXML
    void onConfirmButtonClicked() {
        if (!isValidInput()) {
            displayError("Invalid input.");
            return;
        }

        Article article = (Article) EditViewModel.getSourceToEdit();
        article.setTitle(titleTextField.getText());
        article.setUrl(urlTextField.getText());
        model.updateSource(article);

        model.resetForm();
        closeStage();
    }


    private void checkAssertions() {
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'url-form-view.fxml'.";
    }

    private boolean isValidInput() {
        return StringUtil.isValidURL(urlTextField.getText())
                && !(titleTextField.getText().isBlank());
    }

    private void closeStage() {
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    private void fillForm() {
        if (EditViewModel.getSourceToEdit() != null && (EditViewModel.getSourceToEdit() instanceof Article article)) {
            setTextForFields(article);
        } else
            throw new IllegalStateException("No article selected in the model.");
    }

    private void setTextForFields(Article article) {
        titleTextField.setText(article.getTitle());
        urlTextField.setText(article.getUrl());
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

}

