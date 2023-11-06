package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChoreTest {
    private Chore chore;
    private LocalDate timeFrom;
    private LocalDate timeTo;
    private String choreName;
    private boolean isWeekly;
    private int points;

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

    @BeforeEach
    public void populateChore() {
        this.timeFrom = LocalDate.of(2021, 1, 1);
        this.timeTo = LocalDate.of(2021, 1, 2);
        this.choreName = "Vaske";
        this.isWeekly = false;
        this.points = 10;
        this.chore = new Chore(this.choreName, this.timeFrom, this.timeTo, this.isWeekly,
                this.points, "#FFFFFF", "Creator", "Assignee");
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Chore(this.choreName, this.timeFrom, this.timeTo, false, 10,
                "#FFFFFF", "Creator", "Assignee"));
    }

    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> this.chore.getName());
        assertTrue(this.choreName.equals(this.chore.getName()));
    }

    @Test
    public void testGetTimeFrom() {
        assertDoesNotThrow(() -> this.chore.getTimeFrom());
        assertTrue(this.timeFrom.equals(this.chore.getTimeFrom()));
    }

    @Test
    public void testGetTimeTo() {
        assertDoesNotThrow(() -> this.chore.getTimeTo());
        assertTrue(this.timeTo.equals(this.chore.getTimeTo()));
    }

    @Test
    public void testGetIsWeekly() {
        assertDoesNotThrow(() -> this.chore.getIsWeekly());
        assertEquals(this.isWeekly, this.chore.getIsWeekly());
    }

    @Test
    public void testGetPoints() {
        assertDoesNotThrow(() -> this.chore.getPoints());
        assertEquals(this.points, this.chore.getPoints());
    }

    @Test
    public void testSetPoints() {
        // Test that the points are set correctly for positive values
        int setPoints = 1;
        assertDoesNotThrow(() -> this.chore.setPoints(setPoints));
        assertEquals(setPoints, this.chore.getPoints());

        // Test for negative values
        assertThrows(IllegalArgumentException.class, () -> this.chore.setPoints(-1));
    }

    @Test
    public void testEncodeToJSON() {
        assertDoesNotThrow(() -> Chore.encodeToJSONObject(this.chore));
    }
}