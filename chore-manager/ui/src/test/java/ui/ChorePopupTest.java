package ui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Chore;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * Test that the chore popup works as expected.
 */
public class ChorePopupTest extends BasicTestClass {

    private static final String fxmlFileName = "ChorePopup.fxml";
    private Chore chore;

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    /**
     * Pass the chore and assignee to the controller.
     */
    @BeforeEach
    private void passData() {
        this.chore = new Chore("Vaske", LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), false,
                0, "#000000", testPerson.getUsername());

        Platform.runLater(() -> {
            ChorePopupController controller = this.fxmlLoader.getController();
            controller.passData(chore, testPerson);
        });
    }

    /**
     * Test that the chore gets checked if the user clicks the checkbox.
     */
    @Test
    public void testCheck() {
        CheckBox checkBox = lookup("#checkbox").query();
        this.clickOn(checkBox);

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(this.chore.getChecked());

        this.clickOn(checkBox);

        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(this.chore.getChecked());
    }

    /**
     * Test that the chore gets deleted if the user clicks the delete button.
     */
    @Test
    public void testDelete() {
        Button delete = lookup("#delete").query();

        this.clickOn(delete);

        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(testPerson.getChores().contains(this.chore));
    }
}
