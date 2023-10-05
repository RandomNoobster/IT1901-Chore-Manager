package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.FileHandling.Storage;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private Parent root;
    private AppController controller;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final Person testPerson = new Person("Test");

    // Set environment to testing
    static {
        Storage.getInstance(filePath);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        this.root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();

        // CSS
        Scene scene = new Scene(this.root);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        // Title
        stage.setTitle("Chore Manager");

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        Storage.deleteInstance();
        Storage.getInstance(filePath);
        Storage.getInstance().addPerson(this.testPerson);
    }

    @AfterEach
    public void clearItems() {
        Storage.getInstance().deleteFile();
    }

    public Parent getRootNode() {
        return this.root;
    }

    @Test
    public void testController() {
        assertNotNull(this.controller);
    }
}
