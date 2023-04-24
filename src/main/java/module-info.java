module simple.test12345 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens simple.test12345 to javafx.fxml;
    exports simple.test12345;
}