package core.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChoreTest {
    Chore chore;
    LocalDate timeFrom;
    LocalDate timeTo;
    String choreName;
    boolean isWeekly;
    int points;

    @BeforeEach
    public void populateChore() {
        this.timeFrom = LocalDate.of(2021, 1, 1);
        this.timeTo = LocalDate.of(2021, 1, 2);
        this.choreName = "Vaske";
        this.isWeekly = false;
        this.points = 10;
        this.chore = new Chore(this.choreName, this.timeFrom, this.timeTo, this.isWeekly, this.points);
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Chore(this.choreName, this.timeFrom, this.timeTo, false, 10));
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
    public void testAddPoints() {
        assertDoesNotThrow(() -> this.chore.addPoints(10));
        assertEquals(20, this.chore.getPoints());
    }

    @Test
    public void testEncodeToJSON() {
        assertDoesNotThrow(() -> this.chore.encodeToJSON());
    }
}