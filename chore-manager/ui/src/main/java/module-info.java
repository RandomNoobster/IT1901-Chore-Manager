module main.ui {
    requires transitive main.core;
    requires main.persistence;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires org.json;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml;

    exports ui;
    exports ui.dataAccessLayer;
}
