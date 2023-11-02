package ui;

import java.io.IOException;
import java.util.Set;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class ChorePopupTest extends ApplicationTest {
    private Parent root;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final static Collective testCollective = new Collective("Test Collective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    private final static Person testPerson = new Person("Test", testCollective);
    private Chore chore;

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
        State.getInstance().setCurrentCollective(testCollective);
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
        chore = new Chore("Vaske gulv", null, null, false, 5, "#000000", null);
        testPerson.addChore(chore);
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
        // click on a chore to get the correct view
        Set<Node> temp = this.lookup(".padding-medium").queryAll();

        CheckBox checkBox = this.lookup("#checkbox").query();
        this.clickOn(checkBox);

        WaitForAsyncUtils.waitForFxEvents();
        // assert that the checked status was changed
    }

    // Test that delete works

    // Test that the go back button doesn't change or delete the chore
}
