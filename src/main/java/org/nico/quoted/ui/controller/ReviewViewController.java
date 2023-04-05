package org.nico.quoted.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ReviewViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button nextQuoteButton;

    @FXML
    private Label quoteOriginTitle;

    @FXML
    private Text quoteText;

    @FXML
    void initialize() {
        assert nextQuoteButton != null : "fx:id=\"nextQuoteButton\" was not injected: check your FXML file 'review-view.fxml'.";
        assert quoteOriginTitle != null : "fx:id=\"quoteOriginTitle\" was not injected: check your FXML file 'review-view.fxml'.";
        assert quoteText != null : "fx:id=\"quoteText\" was not injected: check your FXML file 'review-view.fxml'.";

    }

}
