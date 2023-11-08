package ui;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
 * Basic test class that all other test classes should extend.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class BaseTestClass extends ApplicationTest {

    protected Parent root;
    protected String fxmlFileName;
    protected FXMLLoader fxmlLoader;
    protected static Collective testCollective;
    protected static Person testPerson;

    private static final String filePath = "chore-manager-data-ui-test.json";

    /**
     * Sets the current environment to test.
     */
    @BeforeAll
    public void setTestEnvironment() {
        System.setProperty("env", "test");
        Storage.getInstance().deleteFile();
        this.setup();
    }

    /**
     * Sets up the test environment.
     */
    protected void setup() {
        Storage.deleteInstance();

        testCollective = new Collective("Test Collective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);

        testPerson = new Person("Test", testCollective);

        Storage.getInstance().addCollective(testCollective);
        testPerson.setCollective(testCollective);
        Storage.getInstance().addPerson(testPerson, testPerson.getCollective().getJoinCode());
        testCollective.addPerson(testPerson);
        
        State.getInstance().setLoggedInUser(testPerson);
    }

    /**
     * Gets the file name of the FXML file.
     *
     * @return the file name of the FXML file
     */
    protected String getFileName() {
        return this.fxmlFileName;
    }

    /**
     * Basic start method that is used to load the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.fxmlLoader = new FXMLLoader(this.getClass().getResource(this.getFileName()));
        this.root = this.fxmlLoader.load();

        // CSS
        Scene scene = new Scene(this.root);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());

        // Title
        stage.setTitle("Chore Manager");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Before each test, reset the environment.
     */
    @BeforeEach
    public void setupItems() {
        this.setup();
    }

    /**
     * After each test, clear the environment.
     */
    @AfterEach
    public void clearItems() {
        Storage.getInstance().deleteFile();
    }

    /**
     * After all tests, delete the file.
     */
    @AfterAll
    public void deleteFile() {
        if (Storage.getInstance().getFilePath().equals(filePath)) {
            Storage.getInstance().deleteFile();
        }
    }
}
