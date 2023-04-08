package org.nico.quoted.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.nico.quoted.domain.Quote;

import static org.nico.quoted.ui.controller.BaseController.model;

public class ReviewViewController {

    @FXML
    private Button nextQuoteButton;

    @FXML
    private Label quoteOriginTitle;

    @FXML
    private Text quoteText;

    @FXML
    void initialize() {
        checkAssertions();
        setNextRandomQuote();
    }

    private void setNextRandomQuote() {
        Quote quote = model.getRandomQuote();
        quoteText.setText(quote.getText());
        quoteOriginTitle.setText(quote.getSource().getOrigin());
    }

    private void checkAssertions() {
        assert nextQuoteButton != null : "fx:id=\"nextQuoteButton\" was not injected: check your FXML file 'review-view.fxml'.";
        assert quoteOriginTitle != null : "fx:id=\"quoteOriginTitle\" was not injected: check your FXML file 'review-view.fxml'.";
        assert quoteText != null : "fx:id=\"quoteText\" was not injected: check your FXML file 'review-view.fxml'.";
    }

    public void onNextQuoteButtonClicked(ActionEvent actionEvent) {
        setNextRandomQuote();
    }
}
