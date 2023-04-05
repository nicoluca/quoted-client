package org.nico.quoted.ui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.nico.quoted.domain.Quotable;

public class SourceTableViewController extends BaseController {

    @FXML
    private Button addResourceButton;

    @FXML
    private TableView<Quotable> sourceTableView;

    @FXML
    private TableColumn<Quotable, String> titleColumn;

    @FXML
    private TableColumn<Quotable, String> originColumn;

    @FXML
    private TableColumn<Quotable, Button> editButtonColumn;

    @FXML
    private TableColumn<Quotable, String> typeColumn;

    @FXML
    void initialize() {
        checkAssertions();
        initializeTableView();

    }

    private void initializeTableView() {
        sourceTableView.setItems(model.sources);
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        originColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrigin()));
        setDeleteButtonColumn();
    }

    private void setDeleteButtonColumn() {
        editButtonColumn.setCellFactory(new Callback<TableColumn<Quotable, Button>, TableCell<Quotable, Button>>() {
            @Override
            public TableCell<Quotable, Button> call(TableColumn<Quotable, Button> bookButtonTableColumn) {
                TableCell<Quotable, Button> cell = new TableCell<>() {

                    Button editButton = new Button("Edit");
                    @Override
                    protected void updateItem(Button button, boolean empty) {
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // wenn button gedrückt, führe event handler aus
                            editButton.setOnAction(event -> {
                                // TODO
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

    private void checkAssertions() {
        assert addResourceButton != null : "fx:id=\"addResourceButton\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert editButtonColumn != null : "fx:id=\"editButtonColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert originColumn != null : "fx:id=\"originColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert sourceTableView != null : "fx:id=\"sourceTableView\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'source-table-view.fxml'.";
    }

}
