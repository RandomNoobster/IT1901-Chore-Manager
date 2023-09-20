package ui;

import java.io.IOException;

import core.FileHandling.Storage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        // CSS
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        stage.setTitle("Chore Manager");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            Storage.save();
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}