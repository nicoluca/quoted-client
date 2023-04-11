package org.nico.quoted.ui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.nico.quoted.config.LOGGER;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.ui.controller.form.QuoteFormView;

public class BrowseViewController extends MainController {

    @FXML
    private TableView<Quote> quoteTableView;
    @FXML
    private TableView<SourceInterface> sourceTableView;
    @FXML
    private TextField searchTextField;

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
        fillSourceTable(model.getSources());
        fillQuoteTable(model.getQuotes());
        bindSourceSelection();
        bindQuoteSelection();
        bindSearch();
        watchForChanges();
    }

    private void watchForChanges() {
        model.selectedSourceProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                fillQuoteTable(model.getQuotes());
        });

        model.selectedQuoteProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                fillSourceTable(model.getSources());
        });
    }

    private void bindSearch() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fillQuoteTable(model.getQuotesBySearch(newValue));
            fillSourceTable(model.getSourcesBySearch(newValue));
        });
    }

    private void bindQuoteSelection() {
        model.selectedQuoteProperty().bind(quoteTableView.getSelectionModel().selectedItemProperty());
    }

    private void bindSourceSelection() {
        bindSourceSelevtionToQuoteDisplayed();
        // model.selectedSourceProperty().bind(sourceTableView.getSelectionModel().selectedItemProperty());
        refreshOnTabChanged();
    }

    private void bindSourceSelevtionToQuoteDisplayed() {
        sourceTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LOGGER.info("Selected source changed");
                fillQuoteTable(model.getQuotesBySource(newValue));
            }
        });
    }

    private void refreshOnTabChanged() {
        model.resetFormProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                resetView();
        });
    }

    private void resetView() {
        LOGGER.info("Resetting browse form");
        sourceTableView.getSelectionModel().clearSelection();
        quoteTableView.getSelectionModel().clearSelection();
        searchTextField.setText("Search ...");
        fillQuoteTable(model.getQuotes());
        fillSourceTable(model.getSources());
    }

    private void fillQuoteTable(ObservableList<Quote> currentQuotes) {
        quoteTableView.setItems(currentQuotes);
        quoteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
        fillEditQuoteColumn();
        fillDeleteQuoteColumn();
    }

    private void fillDeleteQuoteColumn() {
        deleteQuoteColumn.setCellFactory(bookButtonTableColumn -> {
            TableCell<Quote, Button> cell = new TableCell<>() {

                Button deleteButton = new Button("Delete");
                @Override
                protected void updateItem(Button button, boolean empty) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // wenn button gedrückt, führe event handler aus
                        deleteButton.setOnAction(event -> deleteQuote());
                        setText(null);
                        setGraphic(deleteButton);
                    }
                }
            };

            return cell;
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

    private void fillSourceTable(ObservableList<SourceInterface> currentSources) {
        sourceTableView.setItems(currentSources);
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
