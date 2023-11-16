package ui.viewClasses;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import javafx.stage.Stage;
import ui.BaseTestClass;

/**
 * Test that the day view works as expected.
 */
public class ChoreViewTest extends BaseTestClass {

    private static Chore chore1;
    private static Chore chore2;

    @Override
    public void start(Stage stage) throws IOException {
        // do nothing
    }

    @BeforeAll
    public static void boot() {
        // create chore with dark color and overdue
        chore1 = new Chore("Vaske", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 2), 1,
                "#000000", testPerson.getUsername(), testPerson.getUsername());
        testPerson.addChore(chore1);

        // create chore with light color and checked
        chore2 = new Chore("Vaske", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 2), 1,
                "#FFFFFF", testPerson.getUsername(), testPerson.getUsername());
        chore2.setChecked(true);
        testPerson.addChore(chore2);
    }

    /**
     * Test that the day view can be constructed.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new ChoreView(chore1, testPerson.getUsername()));
        assertDoesNotThrow(() -> new ChoreView(chore2, testPerson.getUsername()));
    }

    /**
     * Test that {@link ChoreView#updateWidth} does not throw any errors.
     */
    @Test
    public void testUpdateWith() {
        double newWidth = 100;
        ChoreView choreView = new ChoreView(chore1, testPerson.getUsername());
        assertDoesNotThrow(() -> choreView.updateWidth(newWidth));
    }

    /**
     * Test that {@link ChoreView#getFxml} does not throw any errors.
     */
    @Test
    public void testGetFxml() {
        ChoreView choreView = new ChoreView(chore1, testPerson.getUsername());
        assertDoesNotThrow(() -> choreView.getFxml());
    }

    /**
     * Test that {@link ChoreView#getContainer} does not throw any errors.
     */
    @Test
    public void testGetContainer() {
        ChoreView choreView = new ChoreView(chore1, testPerson.getUsername());
        assertDoesNotThrow(() -> choreView.getContainer());
    }

}
