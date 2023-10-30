package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class ChorePopupTest extends ApplicationTest {
    private Parent root;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    private final static Person testPerson = new Person("Test", null);

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

        State.getInstance().setLoggedInUser(testPerson);
    }

    @BeforeAll
    public static void setupAll() {
        setup();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ChorePopup.fxml"));
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

    @Test
    public void testCheck() {
        CheckBox checkBox = this.lookup("#checkbox").query();
        this.clickOn(checkBox);

        this.clickOn("#joinCollectiveButton");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(
                State.getInstance().getCurrentCollective().getJoinCode().equals(Collective.LIMBO_COLLECTIVE_JOIN_CODE));
    }

    @Test
    public void testBlankField() {
        this.clickOn("#joinCollectiveButton");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(State.getInstance().getCurrentCollective() == null);
    }
}
