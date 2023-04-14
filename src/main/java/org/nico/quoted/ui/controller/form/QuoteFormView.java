package org.nico.quoted.ui.controller.form;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class QuoteFormView {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteFormView.class);


    public void show() {
        try {
            Parent bookFormView = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource("/org/nico/quoted/form/quote-form-view.fxml")));
            Scene scene = new Scene(bookFormView);

            Stage bookFormStage = new Stage();
            bookFormStage.setScene(scene);
            bookFormStage.setTitle("Edit Quote");
            bookFormStage.show();
        } catch (IOException e) {
            LOGGER.error("quote-form-view.fxml could not be found");
            throw new RuntimeException(e);
        }
    }
}
