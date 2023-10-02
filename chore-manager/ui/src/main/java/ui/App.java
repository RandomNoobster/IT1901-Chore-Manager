package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.FileHandling.Storage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println("Test0");

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        Parent parent = fxmlLoader.load();

        System.out.println("Test1");

        /*
         * AppController controller = fxmlLoader.getController();
         * controller.setStage(stage);
         */

        // To be replaced
        Image icon = new Image(this.getClass().getResource("Icon.png").toExternalForm());
        stage.getIcons().add(icon);

        System.out.println("Test2");

        // CSS
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        System.out.println("Test3");

        // Title
        stage.setTitle("Chore Manager");

        stage.setScene(scene);
        stage.show();

        System.out.println("Test4");

        stage.setOnCloseRequest(event -> {
            Storage.save();
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}