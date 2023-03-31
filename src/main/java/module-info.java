module com.example.quoted {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quoted to javafx.fxml;
    exports com.example.quoted;
}