package org.nico.quoted.ui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Source;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.model.EditViewModel;
import org.nico.quoted.ui.controller.form.QuoteFormView;

@Slf4j
public class QuotesViewController extends MainController {

    @FXML
    private TableView<Quote> quoteTableView;
    @FXML
    private TableView<Source> sourceTableView;
    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Quote, Button> editQuoteColumn;

    @FXML
    private TableColumn<Quote, Button> deleteQuoteColumn;

    @FXML
    private TableColumn<Quote, String> quoteColumn;

    @FXML
    private TableColumn<Source, String> sourceColumn;

    @FXML
    private Button resetButton;

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

    private void bindSourceSelection() {
        showQuotesBasedOnSelectedSource();
        refreshOnTabChanged();
    }

    private void bindQuoteSelection() {
        model.selectedQuoteProperty().bind(quoteTableView.getSelectionModel().selectedItemProperty());
    }

    private void bindSearch() {
        resetView();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fillQuoteTable(model.searchQuotes(newValue));
            fillSourceTable(model.searchSources(newValue));
        });
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


    private void showQuotesBasedOnSelectedSource() {
        sourceTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSource, newSource) -> {
            if (newSource != null) {
                log.info("Selected source changed, showing quotes");
                updateFilteredQuoteTable(newSource);
            }
        });
    }

    private void updateFilteredQuoteTable(Source newSource) {
        fillQuoteTable(model.getQuotesBySource(newSource));
    }

    private void refreshOnTabChanged() {
        model.registerResetListener((observable, oldValue, newValue) -> {
            log.info("Tab changed, refreshing browse view");
            resetView();
        });
    }

    private void resetView() {
        log.info("Resetting browse form");
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
        deleteQuoteColumn.setCellFactory(bookButtonTableColumn -> new TableCell<>() {

            final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Button button, boolean empty) {
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // TODO Faulty behaviour when filtering per source is selected - can we query by quote and not by index here?
                    deleteButton.setOnAction(event -> {
                        Quote quote = getTableView().getItems().get(getIndex());
                        model.deleteQuote(quote);
                        Source selectedSource = sourceTableView.getSelectionModel().getSelectedItem();
                        if (selectedSource != null)
                            updateFilteredQuoteTable(selectedSource);
                        else
                            fillQuoteTable(model.getQuotes());
                    });
                    // deleteButton.setOnAction(event -> model.deleteQuoteByIndex(getIndex()));
                    setText(null);
                    setGraphic(deleteButton);
                }
            }
        });
    }


    private void fillEditQuoteColumn() {
        editQuoteColumn.setCellFactory(bookButtonTableColumn -> new TableCell<>() {

            final Button editButton = new Button("Edit");

            @Override
            protected void updateItem(Button button, boolean empty) {
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    editButton.setOnAction(event -> {
                        // Index does not work here, because of filtering
                        Quote quote = getTableView().getItems().get(getIndex());
                        EditViewModel.setQuoteToEdit(quote);
                        openEditView();
                    });
                    setText(null);
                    setGraphic(editButton);
                }
            }
        });
    }

    private void openEditView() {
        new QuoteFormView().show();
    }

    private void fillSourceTable(ObservableList<Source> currentSources) {
        sourceTableView.setItems(currentSources);
        sourceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        // TODO why here origin and not title -> make clear
    }

    private void checkAssertions() {
        assert quoteTableView != null : "fx:id=\"quoteTableView\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert sourceTableView != null : "fx:id=\"sourceTableView\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert editQuoteColumn != null : "fx:id=\"editQuoteColumn\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert quoteColumn != null : "fx:id=\"quoteColumn\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert sourceColumn != null : "fx:id=\"sourceColumn\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert deleteQuoteColumn != null : "fx:id=\"deleteQuoteColumn\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'quotes-view.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'quotes-view.fxml'.";
    }

    public void onResetButtonClick() {
        resetView();
    }
}
