package org.nico.quoted.util;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtil {
    public static File chooseFile(Button button) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenDialog(button.getScene().getWindow());
    }

    public static Image getImageFromPath(String coverPath) {
        return new Image(new File(coverPath).toURI().toString());
    }
}
