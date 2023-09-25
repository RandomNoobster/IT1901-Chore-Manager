module main.ui {
    requires main.core;
    requires main.persistance;
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens ui to javafx.graphics, javafx.fxml;
}
