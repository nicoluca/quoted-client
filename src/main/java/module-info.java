module org.nico.quoted {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.nico.quoted to javafx.fxml;
    exports org.nico.quoted;
}