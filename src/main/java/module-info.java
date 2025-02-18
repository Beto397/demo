module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jfr;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    opens com.example.preg to javafx.graphics;
    exports com.example.preg;
}

