package org.nico.quoted;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

import static org.nico.quoted.config.UIConfig.*;

@Slf4j
public class QuoteApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        log.info("Starting application");
        FXMLLoader fxmlLoader = new FXMLLoader(QuoteApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(
                Objects.requireNonNull(QuoteApplication.class.getClassLoader().getResource("css/style.css")).toExternalForm()
        );

        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}