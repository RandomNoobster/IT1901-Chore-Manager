package ui.viewClasses;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import core.data.Chore;
import javafx.stage.Stage;
import ui.BaseTestClass;

/**
 * Test that the day view works as expected.
 */
public class ChoreViewTest extends BaseTestClass {

    @Override
    public void start(Stage stage) throws IOException {
        // do nothing
    }

    /**
     * Test that the day view can be constructed.
     */
    @Test
    public void testConstructor() {
        Chore chore = new Chore("Vaske", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 2), false,
                1, "#000000", testPerson.getUsername());
        assertDoesNotThrow(() -> new ChoreView(chore, testPerson));
    }

}
