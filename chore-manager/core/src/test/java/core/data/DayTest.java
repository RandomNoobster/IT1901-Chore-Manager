package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Test that Days have the expected behavior.
 */
public class DayTest extends BaseTestClass {
    private Day day;
    private LocalDate date;

    /**
     * Before each test, create a new Day with some sample values.
     */
    @BeforeEach
    public void populateDay() {
        this.date = LocalDate.of(2020, 1, 1);
        this.day = new Day(this.date);
    }

    /**
     * Test that {@link Day#getDate} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetDate() {
        assertDoesNotThrow(() -> this.day.getDate());
        assertEquals(this.date, this.day.getDate());
    }

    /**
     * Test that {@link Day#setDate} doesn't throw any errors and sets the value correctly.
     */
    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2020, 1, 2);
        assertDoesNotThrow(() -> this.day.setDate(newDate));
        assertEquals(newDate, this.day.getDate());
    }

    @Test
    public void testEquals() {
        // Assert that the same date are equal
        Day day2 = new Day(this.date);
        assertEquals(this.day, day2);

        // Assert that different dates are not equal
        LocalDate newDate = LocalDate.of(2020, 1, 2);
        Day day3 = new Day(newDate);
        assertNotEquals(this.day, day3);
    }

}
