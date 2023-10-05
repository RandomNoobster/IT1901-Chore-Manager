package ui;

import java.io.IOException;
import java.time.LocalDate;

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

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        Parent parent = fxmlLoader.load();
        scene = new Scene(parent);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        // To be replaced
        Image icon = new Image(this.getClass().getResource("Icon.png").toExternalForm());
        stage.getIcons().add(icon);

        // Title
        stage.setTitle("Chore Manager");

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            Storage.save();
            System.exit(0);
        });

    }

    public static void switchScene(String fxmlName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlName + ".fxml"));
            Parent parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void setChoreCreationScene(String fxmlName, LocalDate dateFrom, LocalDate dateTo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlName + ".fxml"));
            Parent parent = fxmlLoader.load();

            ChoreCreationController controller = fxmlLoader.getController();
            controller.passData(dateFrom, dateTo);

            scene.setRoot(parent);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}