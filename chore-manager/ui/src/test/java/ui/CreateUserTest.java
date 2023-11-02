package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class CreateUserTest extends ApplicationTest {
    private Parent root;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    private TextField username = null;
    private TextField displayname = null;
    private PasswordField password = null;
    private Button create = null;
    private Button goBack = null;

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
    }

    @BeforeAll
    public static void setupAll() {
        setup();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreateUser.fxml"));
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
        this.username = this.lookup("#username").query();
        this.displayname = this.lookup("#displayName").query();
        this.password = this.lookup("#password").query();
        this.create = this.lookup("#createButton1").query();
        this.goBack = this.lookup("#goBack").query();
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

    @Test
    public void testSuccessfulCreate() {
        this.interact(() -> {
            this.username.setText("Ole Petter");
        });

        this.interact(() -> {
            this.displayname.setText("OlePetter69420");
        });

        this.interact(() -> {
            this.password.setText("Password12345");
        });

        this.clickOn(this.create);

        WaitForAsyncUtils.waitForFxEvents();
        assertNotNull(Storage.getInstance().getAllPersons().get("Ole Petter"));
    }

    @Test
    public void testAlreadyExists() {
        this.interact(() -> {
            this.username.setText("Kristoffer");
        });

        this.interact(() -> {
            this.displayname.setText("Kristoffer69420");
        });

        this.interact(() -> {
            this.password.setText("Password12345");
        });

        List<Person> preClick = new ArrayList<>(Storage.getInstance().getAllPersonsList());
        this.clickOn(this.create);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getAllPersonsList());

    }

    @Test
    public void testBackButton() {
        List<Person> preClick = new ArrayList<>(Storage.getInstance().getAllPersonsList());

        this.clickOn(goBack);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getAllPersonsList());
    }
}
