package org.nico.quoted;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.nico.quoted.config.Config;
import org.nico.quoted.util.FormUtil;
import org.nico.quoted.util.HttpUtil;

import java.io.IOException;
import java.util.Objects;

import static org.nico.quoted.config.UIConfig.*;

@Slf4j
public class QuoteApplication extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        log.info("Starting application");

        checkServerHealth();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(
                Objects.requireNonNull(
                        getClass().getResource("main-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        FormUtil.addCssToScene(scene);

        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    private static void checkServerHealth() {
        try {
            log.info("Checking server health");
            HttpResponse response = HttpUtil.get(Config.HEALTH_URL);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                showServerNotAvailableAlert();

        } catch (IOException e) {
            showServerNotAvailableAlert();
        }
    }

    private static void showServerNotAvailableAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Server is not available. Please try again later or check your configuration.");
        alert.showAndWait();
        System.exit(1);
    }
}