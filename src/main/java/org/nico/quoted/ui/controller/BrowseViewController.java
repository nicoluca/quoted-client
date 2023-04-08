package org.nico.quoted.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.ui.controller.form.QuoteFormView;

public class BrowseViewController extends BaseController {

    @FXML
    private TableView<Quote> quoteTableView;
    @FXML
    private TableView<SourceInterface> sourceTableView;
    @FXML
    private TextField searchTextField;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Quote, Button> editQuoteColumn;

    @FXML
    private TableColumn<Quote, Button> deleteQuoteColumn;

    @FXML
    private TableColumn<Quote, String> quoteColumn;

    @FXML
    private TableColumn<SourceInterface, String> sourceColumn;

    @FXML
    void initialize() {
        checkAssertions();
        fillSourceTable();
        fillQuoteTable();
        bindSourceSelection();
        bindQuoteSelection();
    }

    private void bindQuoteSelection() {
        model.selectedQuoteProperty().bind(quoteTableView.getSelectionModel().selectedItemProperty());
    }

    private void bindSourceSelection() {
        model.selectedSourceProperty().bind(sourceTableView.getSelectionModel().selectedItemProperty());
    }

    private void fillQuoteTable() {
        quoteTableView.setItems(model.quotes);
        quoteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
        fillEditQuoteColumn();
        fillDeleteQuoteColumn();
    }

    private void fillDeleteQuoteColumn() {
        deleteQuoteColumn.setCellFactory(new Callback<TableColumn<Quote, Button>, TableCell<Quote, Button>>() {
            @Override
            public TableCell<Quote, Button> call(TableColumn<Quote, Button> bookButtonTableColumn) {
                TableCell<Quote, Button> cell = new TableCell<>() {

                    Button deleteButton = new Button("Delete");
                    @Override
                    protected void updateItem(Button button, boolean empty) {
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // wenn button gedrückt, führe event handler aus
                            deleteButton.setOnAction(event -> {
                                deleteQuote();
                            });
                            setText(null);
                            setGraphic(deleteButton);
                        }
                    }
                };

                return cell;
            }
        });
    }

    private void deleteQuote() {
        model.deleteQuote();
    }

    private void fillEditQuoteColumn() {
        editQuoteColumn.setCellFactory(new Callback<TableColumn<Quote, Button>, TableCell<Quote, Button>>() {
            @Override
            public TableCell<Quote, Button> call(TableColumn<Quote, Button> bookButtonTableColumn) {
                TableCell<Quote, Button> cell = new TableCell<>() {

                    Button editButton = new Button("Edit");
                    @Override
                    protected void updateItem(Button button, boolean empty) {
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // wenn button gedrückt, führe event handler aus
                            editButton.setOnAction(event -> {
                                // Open edit view and pass selected quote
                                openEditView();
                            });
                            setText(null);
                            setGraphic(editButton);
                        }
                    }
                };

                return cell;
            }
        });
    }

    private void openEditView() {
        new QuoteFormView().show();
    }

    private void fillSourceTable() {
        sourceTableView.setItems(model.sources);
        sourceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrigin()));
        // TODO why here origin and not title -> make clear
    }

    private void checkAssertions() {
        assert quoteTableView != null : "fx:id=\"quoteTableView\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert sourceTableView != null : "fx:id=\"sourceTableView\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert editQuoteColumn != null : "fx:id=\"editQuoteColumn\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert quoteColumn != null : "fx:id=\"quoteColumn\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert sourceColumn != null : "fx:id=\"sourceColumn\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert deleteQuoteColumn != null : "fx:id=\"deleteQuoteColumn\" was not injected: check your FXML file 'browse-view.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'browse-view.fxml'.";
    }

}
