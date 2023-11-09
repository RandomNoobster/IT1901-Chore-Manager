package ui;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

import core.data.Chore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import persistence.fileHandling.EnvironmentConfigurator;
import ui.dataAccessLayer.DataAccess;
import ui.dataAccessLayer.RemoteDataAccess;

/**
 * The App class has the logic to start the user interface of the project.
 */
public class App extends Application {

    // Cannot be null, will cause exception when switching scenes
    private static Scene scene = new Scene(new Pane());
    private static DataAccess dataAccess; // Caching purposes

    /**
     * The start method is called when the application is launched. It loads the FXML-file and sets
     * the scene.
     *
     * @param stage The stage to be used for the application
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        Parent parent = fxmlLoader.load();
        setScene(parent);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        // To be replaced
        Image icon = new Image(this.getClass().getResource("Icon.png").toExternalForm());
        stage.getIcons().add(icon);

        // Title
        stage.setTitle("Chore Manager");

        stage.setScene(scene);
        stage.show();

    }

    public static void setScene(Parent parent) {
        scene = new Scene(parent);
    }

    /**
     * This method is called when the user wants to switch to another scene.
     *
     * @param fxmlName The name of the FXML-file to be loaded
     */
    public static void switchScene(String fxmlName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlName + ".fxml"));
            Parent parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called when the user wants to switch to chore creation scene
     *
     * @param dateFrom The start date of the chore
     * @param dateTo   The end date of the chore
     */
    public static void setChoreCreationScene(LocalDate dateFrom, LocalDate dateTo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ChoreCreation.fxml"));
            Parent parent = fxmlLoader.load();

            ChoreCreationController controller = fxmlLoader.getController();
            controller.passData(dateFrom, dateTo);
            scene.setRoot(parent);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called when the user wants to switch to chorepopup scene
     *
     * @param dateFrom The start date of the chore
     * @param dateTo   The end date of the chore
     */
    public static void setChorePopupScene(Chore chore, String assignee) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ChorePopup.fxml"));
            Parent parent = fxmlLoader.load();

            ChorePopupController controller = fxmlLoader.getController();
            controller.passData(chore, assignee);
            scene.setRoot(parent);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the data access layer.
     */
    public static DataAccess getDataAccess() {
        if (dataAccess != null)
            return dataAccess;
        EnvironmentConfigurator configurator = new EnvironmentConfigurator();
        URI apiBaseEndpoint = configurator.getAPIBaseEndpoint();
        if (apiBaseEndpoint != null) {
            dataAccess = new RemoteDataAccess(apiBaseEndpoint);
            return dataAccess;
        } else {
            // Use direct data access here
            throw new RuntimeException("Could not find API base endpoint");
        }
    }

    /**
     * Creates alerts
     * 
     * @param title   Title of alert
     * @param message Additional information that the alert should give
     * @param type    Type of alert, ex: waring, information, ...
     */
    public static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch();
    }
}