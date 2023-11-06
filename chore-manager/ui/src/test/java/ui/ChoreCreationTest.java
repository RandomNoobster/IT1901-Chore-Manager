package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import core.data.Chore;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import persistence.fileHandling.Storage;

/**
 * Test that the chore creation page works as expected.
 */
public class ChoreCreationTest extends BasicTestClass {

    private static final String fxmlFileName = "ChoreCreation.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @Override
    protected void setup() {
        Storage.deleteInstance();
        Storage.getInstance().addCollective(testCollective);
        Storage.getInstance().addPerson(testPerson, testPerson.getCollective().getJoinCode());
        testCollective.addPerson(testPerson);
        State.getInstance().setLoggedInUser(testPerson);
    }

    /**
     * Clicks on the buttons with the given labels
     *
     * @param labels
     */
    private void click(String... labels) {
        for (var label : labels) {
            this.clickOn(LabeledMatchers.hasText(label));
        }
    }

    /**
     * Test that the chore creation page works as expected.
     */
    @Test
    public void testCreateChore() {
        List<Chore> savedChores = Storage.getInstance().getAllChores();

        TextField name = this.lookup("#name").query();
        this.interact(() -> {
            name.setText("Vaske!");
        });

        ComboBox<String> comboBox = this.lookup("#personsMenu").query();
        this.interact(() -> {
            comboBox.getSelectionModel().select(0);
        });

        ColorPicker colorPicker = this.lookup("#colorPicker").query();
        this.interact(() -> {
            colorPicker.setValue(new Color(0.5, 0.5, 0.5, 1));
        });

        Slider slider = this.lookup("#points").query();
        this.interact(() -> {
            slider.setValue(5);
        });

        this.click("Create");

        WaitForAsyncUtils.waitForFxEvents();

        // Ensure that a new Chore has been made
        assertTrue(savedChores.size() + 1 == Storage.getInstance().getAllChores().size());
    }
}
