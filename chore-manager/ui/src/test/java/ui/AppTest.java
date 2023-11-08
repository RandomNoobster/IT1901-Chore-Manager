package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.fileHandling.EnvironmentConfigurator;
import persistence.fileHandling.Storage;
import ui.dataAccessLayer.DataAccess;
import ui.dataAccessLayer.RemoteDataAccess;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private Parent root;
    private AppController controller;
    private static DataAccess dataAccess = getDataAccess();

    /**
     * Gets the data access layer.
     */
    // Kristoffer! Move this to the base test class you made (with the dataAccess field)
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
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
        Storage.deleteInstance();
        Storage.getInstance().deleteFile();
        setup();
    }

    private static void setup() {
        Storage.getInstance().deleteFileContent();
        dataAccess.enterTestMode();

        // Temporary solution. Kristoffer has a better way
        Collective testCollective = new Collective("Test Collective");
        Person testPerson = new Person("Test", testCollective.getJoinCode());

        dataAccess.logIn(testPerson, testPerson.getPassword(), testCollective);
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
        setup();
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
