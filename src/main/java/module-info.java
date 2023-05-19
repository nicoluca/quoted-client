module org.nico.quoted {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // Required for Hibernate
    requires net.bytebuddy;
    requires org.hibernate.commons.annotations;
    requires com.fasterxml.classmate;
    requires jakarta.xml.bind;
    requires httpclient;
    requires httpcore;
    requires com.google.gson;

    opens org.nico.quoted to javafx.fxml;
    exports org.nico.quoted;
    exports org.nico.quoted.ui.controller;
    opens org.nico.quoted.ui.controller to javafx.fxml;
    opens org.nico.quoted.ui.controller.form to javafx.fxml;
    opens org.nico.quoted.domain to org.hibernate.orm.core, com.google.gson;
    opens org.nico.quoted.serialization to org.hibernate.orm.core;
}