package org.nico.quoted.ui.controller.form;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.util.FormUtil;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class QuoteFormView {

    public void show() {
        try {
            Parent bookFormView = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource("/org/nico/quoted/form/quote-form-view.fxml")));
            Scene scene = new Scene(bookFormView);

            FormUtil.addCssToScene(scene);

            Stage bookFormStage = new Stage();
            bookFormStage.setScene(scene);
            bookFormStage.setTitle("Edit Quote");
            bookFormStage.show();
        } catch (IOException e) {
            log.error("quote-form-view.fxml could not be found");
            throw new RuntimeException(e);
        }
    }
}
