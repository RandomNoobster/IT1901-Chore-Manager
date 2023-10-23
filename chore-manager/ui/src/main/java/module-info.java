module main.ui {
    requires transitive main.core;
    requires main.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires json.simple;

    opens ui to javafx.graphics, javafx.fxml;

    exports ui;
}
