package org.nico.quoted.util;

import javafx.scene.Scene;
import org.nico.quoted.config.UIConfig;

import java.util.Objects;

public class FormUtil {
    public static void addCssToScene(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(FormUtil.class.getClassLoader().getResource(UIConfig.CSS_PATH)).toExternalForm()
        );
    }
}
