package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Chore;
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
    private ChoreCreationController controller;
    private static final String filePath = "chore-manager-data-ui-test.json";
    private final Person testPerson = new Person("Test");

    // Set environment to testing
    static {
        Storage.getInstance(filePath);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ChoreCreation.fxml"));
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
        Storage.getInstance().deleteFileContent();
    }

    @AfterAll
    public static void deleteFile() {
        if (Storage.getInstance().getFilePath().equals(filePath)) {
            Storage.getInstance().deleteFile();
        }
    }

    private void click(String... labels) {
        for (var label : labels) {
            this.clickOn(LabeledMatchers.hasText(label));
        }
    }

    @Test
    public void testCreateChore() {
        List<Chore> savedChores = Storage.getInstance().getChoresList();

        TextField name = this.lookup("#name").query();
        this.interact(() -> {
            name.setText("Bob");
        });

        ComboBox<String> comboBox = this.lookup("#personsMenu").query();
        this.interact(() -> {
            comboBox.getSelectionModel().select(0);
        });

        this.click("Create");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(savedChores.size() + 1 == Storage.getInstance().getChoresList().size());
    }
}
