package org.nico.quoted.ui;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtil {
    // TODO: Add a method to get a file from a button
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
}
