package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import core.data.Chore;
import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class LoginTest extends ApplicationTest {
    private Parent root;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective");
    private final static Person testPerson = new Person("Test", testCollective);

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
        Storage.getInstance().deleteFile();
        setup();
    }

    private static void setup() {
        Storage.deleteInstance();
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
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        this.root = fxmlLoader.load();

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
        Storage.getInstance().deleteFileContent();
    }

    @AfterAll
    public static void deleteFile() {
        if (Storage.getInstance().getFilePath().equals(filePath)) {
            Storage.getInstance().deleteFile();
        }
    }

    /**
     * Test that the user gets logged in if they enter the correct credentials
     */
    @Test
    public void testLogin() {
        List<Chore> savedChores = Storage.getInstance().getAllChores();

        TextField name = this.lookup("#username").query();
        this.interact(() -> {
            name.setText(testPerson.getUsername());
        });

        PasswordField pwd = this.lookup("#password").query();
        this.interact(() -> {
            pwd.setText("1234Password213");
        });

        this.clickOn("#login");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(State.getInstance().getLoggedInUser().getUsername().equals(testPerson.getUsername()));
    }

    /**
     * Test that the user does not get logged in if they do not enter username or password.
     */
    @Test
    public void testBlankFields() {
        this.clickOn("#login");

        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(State.getInstance().getLoggedInUser().getUsername().equals("Kristoffer"));
    }
}
