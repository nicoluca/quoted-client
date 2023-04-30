package org.nico.quoted.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.nico.quoted.domain.Quote;

public class RandomViewController extends MainController {

    @FXML
    private Button nextQuoteButton;

    @FXML
    private Label quoteOriginTitle;

    @FXML
    private TextArea quoteText;

    @FXML
    void initialize() {
        checkAssertions();
        setNextRandomQuote();
        setUpQuoteText();
    }

    private void setNextRandomQuote() {
        Quote quote = model.getRandomQuote();
        quoteText.setText(quote.getText());
        setQuoteOrigin(quote);
        // Make label italic
        quoteOriginTitle.setStyle("-fx-font-style: italic");
    }

    private void setUpQuoteText() {
        quoteText.setWrapText(true);
        quoteText.setEditable(false);
    }

    private void setQuoteOrigin(Quote quote) {
        if (quote.getSource().getTitle().equals(quote.getSource().getOrigin()))
            quoteOriginTitle.setText(quote.getSource().getTitle()); // If source is Article with same title and url, only display title
        else
            quoteOriginTitle.setText(quote.getSource().getTitle() + " - " + quote.getSource().getOrigin());
    }

    public void onNextQuoteButtonClicked() {
        setNextRandomQuote();
    }

    private void checkAssertions() {
        assert nextQuoteButton != null : "fx:id=\"nextQuoteButton\" was not injected: check your FXML file 'random-view.fxml'.";
        assert quoteOriginTitle != null : "fx:id=\"quoteOriginTitle\" was not injected: check your FXML file 'random-view.fxml'.";
        assert quoteText != null : "fx:id=\"quoteText\" was not injected: check your FXML file 'random-view.fxml'.";
    }
}
