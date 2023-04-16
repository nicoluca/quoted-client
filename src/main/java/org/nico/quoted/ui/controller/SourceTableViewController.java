package org.nico.quoted.ui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.ui.controller.form.ArticleFormView;
import org.nico.quoted.ui.controller.form.BookFormView;

public class SourceTableViewController extends MainController {

    @FXML
    private TableColumn deleteButtonColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button addBookButton;

    @FXML
    private TableView<SourceInterface> sourceTableView;

    @FXML
    private TableColumn<SourceInterface, String> titleColumn;

    @FXML
    private TableColumn<SourceInterface, String> originColumn;

    @FXML
    private TableColumn<SourceInterface, Button> editButtonColumn;

    @FXML
    private TableColumn<SourceInterface, String> typeColumn;

    @FXML
    void initialize() {
        checkAssertions();
        fillTableView(model.getSources());
        bindSelectedSource();
        setUpSearchField();
        bindResetListener();
    }

    private void bindResetListener() {
        model.registerResetListener((observable, oldValue, newValue) -> {
                    sourceTableView.getSelectionModel().clearSelection();
                    searchTextField.setText("Search ...");
                    fillTableView(model.getSources());
                });
    }

    private void setUpSearchField() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue)
                -> fillTableView(model.searchSources(newValue)));
    }

    private void bindSelectedSource() {
        model.selectedSourceProperty().bind(sourceTableView.getSelectionModel().selectedItemProperty());
    }

    private void fillTableView(ObservableList<SourceInterface> currentSources) {
        sourceTableView.setItems(currentSources);
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        originColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrigin()));
        setEditButtonColumn();
        setDeleteButtonColumn();
    }

    private void setEditButtonColumn() {
        editButtonColumn.setCellFactory(bookButtonTableColumn -> {
            TableCell<SourceInterface, Button> cell = new TableCell<>() {

                Button editButton = new Button("Edit");
                @Override
                protected void updateItem(Button button, boolean empty) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setGraphic(editButton);
                        editButton.setOnAction(event -> editSource());
                    }
                }
            };

            return cell;
        });
    }

    private void editSource() {
        if (model.selectedSourceProperty().get().getType().equals("Book"))
            addOrEditBook();
        else
            editOnlineArticle();
    }

    private void editOnlineArticle() {
        new ArticleFormView().show();
    }

    private void addOrEditBook() {
        new BookFormView().show();
    }


    private void setDeleteButtonColumn() {
        deleteButtonColumn.setCellFactory((Callback<TableColumn<SourceInterface, Button>, TableCell<SourceInterface, Button>>) bookButtonTableColumn -> {
            TableCell<SourceInterface, Button> cell = new TableCell<>() {

                Button deleteButton = new Button("Delete");
                @Override
                protected void updateItem(Button button, boolean empty) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // wenn button gedrückt, führe event handler aus
                        deleteButton.setOnAction(event -> model.deleteSourceByIndex(getIndex()));
                        setText(null);
                        setGraphic(deleteButton);
                    }
                }
            };

            return cell;
        });
    }


    private void checkAssertions() {
        assert addBookButton != null : "fx:id=\"addResourceButton\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert editButtonColumn != null : "fx:id=\"editButtonColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert originColumn != null : "fx:id=\"originColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert sourceTableView != null : "fx:id=\"sourceTableView\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert deleteButtonColumn != null : "fx:id=\"deleteButtonColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";

    }

    public void onAddBookButtonClick(ActionEvent actionEvent) {
        sourceTableView.getSelectionModel().clearSelection();
        addOrEditBook();
    }

}
