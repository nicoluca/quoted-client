module org.nico.quoted {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires static lombok;

    // Required for Hibernate
    requires httpclient;
    requires httpcore;
    requires com.google.gson;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens org.nico.quoted to javafx.fxml;
    exports org.nico.quoted;
    exports org.nico.quoted.ui.controller;
    exports org.nico.quoted.domain to com.fasterxml.jackson.databind;
    opens org.nico.quoted.ui.controller to javafx.fxml;
    opens org.nico.quoted.ui.controller.form to javafx.fxml;
}