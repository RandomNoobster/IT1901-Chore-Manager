package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.fileHandling.EnvironmentConfigurator;
import persistence.fileHandling.Storage;
import ui.dataAccessLayer.DataAccess;
import ui.dataAccessLayer.RemoteDataAccess;

public class ChoreCreationTest extends ApplicationTest {

    private Parent root;
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

        dataAccess.addCollective(testCollective);
        dataAccess.addPerson(testPerson, testPerson.getCollectiveJoinCode());
        dataAccess.logIn(testPerson, testPerson.getPassword(), testCollective);
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
        List<Chore> savedChores = dataAccess.getChores();

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

        assertTrue(savedChores.size() + 1 == dataAccess.getChores().size());
    }
}
