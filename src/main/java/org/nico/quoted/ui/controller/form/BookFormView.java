package org.nico.quoted.ui.controller.form;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nico.quoted.config.Logger;

import java.io.IOException;
import java.util.Objects;

public class BookFormView {
    public void show() {
        try {
            Parent bookFormView = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource("/org/nico/quoted/form/book-form-view.fxml")));
            Scene scene = new Scene(bookFormView);

            Stage bookFormStage = new Stage();
            bookFormStage.setScene(scene);
            bookFormStage.setTitle("Edit Book");
            bookFormStage.show();
        } catch (IOException e) {
            Logger.LOGGER.log(Logger.ERROR, "book-form-view.fxml could not be found");
            throw new RuntimeException(e);
        }
    }
}