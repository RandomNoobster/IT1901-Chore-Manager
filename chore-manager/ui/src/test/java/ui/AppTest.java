package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.State;
import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private Parent root;
    private AppController controller;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective");
    private final static Person testPerson = new Person("Test", testCollective);

    // Set environment to testing
    static {
        Storage.setInstance(filePath);
    }

    private static void setup() {
        Storage.deleteInstance();
        Storage.setInstance(filePath);
        Storage.getInstance().addCollective(testCollective);
        Storage.getInstance().addPerson(testPerson, testPerson.getCollective().getJoinCode());

        State.getInstance().setLoggedInUser(testPerson);
    }

    @BeforeAll
    public static void setupAll() {
        setup();
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

    @AfterAll
    public static void deleteFile() {
        if (Storage.getInstance().getFilePath().equals(filePath)) {
            System.out.println(Storage.getInstance().getFilePath());
            Storage.getInstance().deleteFile();
        }
    }

    public Parent getRootNode() {
        return this.root;
    }

    @Test
    public void testController() {
        assertNotNull(this.controller);
    }
}
