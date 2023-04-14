package org.nico.quoted.ui.controller.form;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class BookFormView {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookFormView.class);


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
            LOGGER.error("book-form-view.fxml could not be found");
            throw new RuntimeException(e);
        }
    }
}
