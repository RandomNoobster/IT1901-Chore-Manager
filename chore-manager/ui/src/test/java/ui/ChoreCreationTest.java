package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import core.data.Chore;
import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.Storage;

public class ChoreCreationTest extends ApplicationTest {

    private Parent root;
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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ChoreCreation.fxml"));
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
        Storage.getInstance().deleteFile();
    }

    private void click(String... labels) {
        for (var label : labels) {
            this.clickOn(LabeledMatchers.hasText(label));
        }
    }

    @Test
    public void testCreateChore() {
        List<Chore> savedChores = Storage.getInstance().getAllChores();

        TextField name = this.lookup("#name").query();
        this.interact(() -> {
            name.setText("Clean up your mess!");
        });

        ComboBox<String> comboBox = this.lookup("#personsMenu").query();
        this.interact(() -> {
            comboBox.getSelectionModel().select(0);
        });

        this.click("Create");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(savedChores.size() + 1 == Storage.getInstance().getAllChores().size());
    }
}
