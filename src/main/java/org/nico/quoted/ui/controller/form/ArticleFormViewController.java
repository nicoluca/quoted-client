package org.nico.quoted.ui.controller.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nico.quoted.ui.controller.MainController;
import org.nico.quoted.domain.Article;
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
    void onConfirmButtonClicked(ActionEvent event) {
        if (!isValidInput()) {
            displayError("Invalid input.");
            return;
        }

        Article article = (Article) model.selectedSourceProperty().get();
        article.setTitle(titleTextField.getText());
        article.setUrl(urlTextField.getText());
        model.updateArticle(article);

        model.resetFormProperty().set(!model.resetFormProperty().get());
        closeStage();

    }

    private boolean isValidInput() {
        return StringUtil.isValidURL(urlTextField.getText())
                && !(titleTextField.getText().isBlank());
    }

    private void closeStage() {
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    private void fillForm() {
        checkSeletion();
        Article article = (Article) model.selectedSourceProperty().get();
        titleTextField.setText(article.getTitle());
        urlTextField.setText(article.getUrl());
    }

    private void checkSeletion() {
        if (model.selectedSourceProperty().get() == null || !(model.selectedSourceProperty().get() instanceof Article))
            throw new IllegalStateException("No article selected in the model.");
    }

    private void displayError(String s) {
        errorLabel.setText(s);
    }

    private void checkAssertions() {
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'url-form-view.fxml'.";
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'url-form-view.fxml'.";
    }

}

