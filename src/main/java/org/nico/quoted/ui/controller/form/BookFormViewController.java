package org.nico.quoted.ui.controller.form;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BookFormViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField authorTextField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button coverFilePathButton;

    @FXML
    private Button exitDeleteButton;

    @FXML
    private TextField titleTextField;

    @FXML
    void initialize() {
        assert authorTextField != null : "fx:id=\"authorTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert coverFilePathButton != null : "fx:id=\"coverFilePathButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert exitDeleteButton != null : "fx:id=\"exitDeleteButton\" was not injected: check your FXML file 'book-form-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'book-form-view.fxml'.";

    }

}
