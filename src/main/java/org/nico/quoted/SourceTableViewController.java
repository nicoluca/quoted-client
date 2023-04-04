package org.nico.quoted;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SourceTableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addResourceButton;

    @FXML
    private TableColumn<?, ?> authorColumn;

    @FXML
    private TableView<?> bookTableView;

    @FXML
    private TableColumn<?, ?> coverPathColumn;

    @FXML
    private TableColumn<?, ?> editButtonColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    void initialize() {
        checkAssertions();
    }

    private void checkAssertions() {
        assert addResourceButton != null : "fx:id=\"addResourceButton\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert authorColumn != null : "fx:id=\"authorColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert bookTableView != null : "fx:id=\"bookTableView\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert coverPathColumn != null : "fx:id=\"coverPathColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert editButtonColumn != null : "fx:id=\"editButtonColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
    }

}
