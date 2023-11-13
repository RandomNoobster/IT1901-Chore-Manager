package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class ChorePopupTest extends BaseTestClass {

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
        this.chore = new Chore("Vaske", LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), 0,
                "#000000", testPerson.getUsername(), testPerson.getUsername());
        dataAccess.addChore(this.chore, testPerson);

        Platform.runLater(() -> {
            ChorePopupController controller = this.fxmlLoader.getController();
            controller.passData(this.chore, testPerson.getUsername());
        });
    }

    /**
     * Test that the chore gets checked if the user clicks the checkbox.
     */
    @Test
    public void testCheck() {
        CheckBox checkBox = this.lookup("#checkbox").query();
        this.clickOn(checkBox);

        WaitForAsyncUtils.waitForFxEvents();
        this.chore = dataAccess.getPerson(testPerson.getUsername(), testPerson.getPassword())
                .getChores().get(0);
        assertTrue(this.chore.getChecked());

        this.clickOn(checkBox);

        WaitForAsyncUtils.waitForFxEvents();
        this.chore = dataAccess.getPerson(testPerson.getUsername(), testPerson.getPassword())
                .getChores().get(0);
        assertFalse(this.chore.getChecked());
    }

    /**
     * Test that the chore gets deleted if the user clicks the delete button.
     */
    @Test
    public void testDelete() {
        Button delete = this.lookup("#delete").query();

        this.clickOn(delete);

        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(testPerson.getChores().contains(this.chore));
    }
}
