module main.ui {
    requires main.core;
    requires main.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires json.simple;
    
    requires transitive javafx.graphics;

    opens ui to javafx.graphics, javafx.fxml;

    exports ui;
}
