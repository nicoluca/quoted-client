package org.nico.quoted.ui.controller;


import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.nico.quoted.config.LOGGER;

public class MainController extends BaseController {

    @FXML
    private TabPane tabPane;
    private ReadOnlyObjectProperty<Tab> selectedTabProperty;

    @FXML
    void initialize() {
        LOGGER.info( "Main controller initialized");
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'main-view.fxml'.";
        selectedTabProperty = tabPane.getSelectionModel().selectedItemProperty();
        addListener((observable, oldValue, newValue) -> onTabSelectionChanged());
    }

    protected static void onTabSelectionChanged() {
        LOGGER.info("Tab registered a change in main controller");
        model.resetFormProperty().set(!model.resetFormProperty().get());
    }

    protected void addListener(ChangeListener listener) {
        if (selectedTabProperty != null)
            selectedTabProperty.addListener(listener);
        else
            LOGGER.warning("Tried to add listener to null property");
    }
}