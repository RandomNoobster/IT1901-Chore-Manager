package core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that Chores have the expected behavior.
 */
public class ChoreTest {
    private Chore chore;
    private LocalDate timeFrom;
    private LocalDate timeTo;
    private String choreName;
    private boolean isWeekly;
    private int points;

    /**
     * Before each test, create a new Chore with some sample values.
     */
    @BeforeEach
    public void populateChore() {
        this.timeFrom = LocalDate.of(2021, 1, 1);
        this.timeTo = LocalDate.of(2021, 1, 2);
        this.choreName = "Vaske";
        this.isWeekly = false;
        this.points = 10;
        this.chore = new Chore(this.choreName, this.timeFrom, this.timeTo, this.isWeekly,
                this.points, "#FFFFFF", "Creator");
    }

    /**
     * Test that the constructor can construct a new object without throwing any errors.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Chore(this.choreName, this.timeFrom, this.timeTo, false, 10,
                "#FFFFFF", "Creator"));
    }

    /**
     * Test that {@link Chore#getName} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> this.chore.getName());
        assertTrue(this.choreName.equals(this.chore.getName()));
    }

    /**
     * Test that {@link Chore#getTimeFrom} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetTimeFrom() {
        assertDoesNotThrow(() -> this.chore.getTimeFrom());
        assertTrue(this.timeFrom.equals(this.chore.getTimeFrom()));
    }

    /**
     * Test that {@link Chore#getTimeTo} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetTimeTo() {
        assertDoesNotThrow(() -> this.chore.getTimeTo());
        assertTrue(this.timeTo.equals(this.chore.getTimeTo()));
    }

    /**
     * Test that {@link Chore#getIsWeekly} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetIsWeekly() {
        assertDoesNotThrow(() -> this.chore.getIsWeekly());
        assertEquals(this.isWeekly, this.chore.getIsWeekly());
    }

    /**
     * Test that {@link Chore#getPoints} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetPoints() {
        assertDoesNotThrow(() -> this.chore.getPoints());
        assertEquals(this.points, this.chore.getPoints());
    }

    /**
     * Test that {@link Chore#setPoints} doesn't throw any errors and sets the value correctly.
     */
    @Test
    public void testSetPoints() {
        // Test that the points are set correctly for positive values
        int setPoints = 1;
        assertDoesNotThrow(() -> this.chore.setPoints(setPoints));
        assertEquals(setPoints, this.chore.getPoints());

        // Test for negative values
        assertThrows(IllegalArgumentException.class, () -> this.chore.setPoints(-1));
    }

    /**
     * Test that {@link Chore#encodeToJSON} doesn't throw any errors.
     */
    @Test
    public void testEncodeToJSON() {
        assertDoesNotThrow(() -> this.chore.encodeToJSON());
    }
}