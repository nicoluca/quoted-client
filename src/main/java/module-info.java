module org.nico.quoted {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens org.nico.quoted to javafx.fxml;
    exports org.nico.quoted;
    exports org.nico.quoted.ui.controller;
    opens org.nico.quoted.ui.controller to javafx.fxml;
    opens org.nico.quoted.ui.controller.form to javafx.fxml;
}