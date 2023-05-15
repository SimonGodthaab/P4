module simple.P4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;

    opens simple.P4 to javafx.fxml;
    exports simple.P4;
    exports simple.P4.Util;
    opens simple.P4.Util to javafx.fxml;
}