package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Chore;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Test that the chore creation page works as expected.
 */
public class ChoreCreationTest extends BaseTestClass {

    private static final String fxmlFileName = "ChoreCreation.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
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
        List<Chore> savedChores = dataAccess.getChores();

        TextField name = this.lookup("#name").query();
        this.interact(() -> {
            name.setText("Clean up your mess!");
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
        assertTrue(savedChores.size() + 1 == dataAccess.getChores().size());
    }
}
