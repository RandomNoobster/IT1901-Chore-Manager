package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class CreateCollectiveTest extends ApplicationTest {
    private Parent root;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    private final static Person testPerson = new Person("Test", null);
    private TextField collectiveName = null;
    private Button createButton = null;
    private Button goBackButton = null;

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

        State.getInstance().setLoggedInUser(testPerson);
    }

    @BeforeAll
    public static void setupAll() {
        setup();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreateCollective.fxml"));
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
        collectiveName = this.lookup("#collectiveNameInput").query();
        goBackButton = this.lookup("#goBackButton").query();
        createButton = this.lookup("#createButton").query();
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
    public void testCreate() {
        this.interact(() -> {
            this.collectiveName.setText("Super Duper Collective");
        });

        int pre = Storage.getInstance().getCollectives().size();
        this.clickOn(this.createButton);

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(pre + 1 == Storage.getInstance().getCollectives().size());
    }

    @Test
    public void testBackButton() {
        HashMap<String, Collective> preClick = new HashMap<>(Storage.getInstance().getCollectives());

        this.clickOn(this.goBackButton);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getCollectives());
    }
}
