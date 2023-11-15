package ui.viewClasses;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Week;
import javafx.stage.Stage;
import ui.BaseTestClass;

/**
 * Test that the day view works as expected.
 */
public class WeekViewTest extends BaseTestClass {

    private static Week week = new Week(LocalDate.of(2023, 11, 6));

    @Override
    public void start(Stage stage) throws IOException {
        // do nothing
    }

    @BeforeEach
    public void boot() {
        // create and add a daily chore to a person in the active collective
        Chore chore1 = new Chore("Vaske", LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 6), 1,
                "#000000", testPerson.getUsername(), testPerson.getUsername());
        testPerson.addChore(chore1);

        // create and add a weekly chore to a person in the active collective
        Chore chore2 = new Chore("Vaske", LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 13), 1,
                "#000000", testPerson.getUsername(), testPerson.getUsername());
        testPerson.addChore(chore2);
    }

    /**
     * Test that the day view can be constructed.
     */
    @Test
    public void testConstructor() {
        // ensure no errors are raised when generating the WeekView object
        assertDoesNotThrow(() -> new WeekView(week));
    }

    /**
     * Test that {@link WeekView#updateWidth} does not throw any errors.
     */
    @Test
    public void testUpdateWidth() {
        double newWidth = 100;
        WeekView weekView = new WeekView(week);
        assertDoesNotThrow(() -> weekView.updateWidth(newWidth));
    }

    /**
     * Test that {@link WeekView#updateHeight} does not throw any errors.
     */
    @Test
    public void testUpdateHeight() {
        double newHeight = 100;
        WeekView weekView = new WeekView(week);
        assertDoesNotThrow(() -> weekView.updateHeight(newHeight));
    }

}
