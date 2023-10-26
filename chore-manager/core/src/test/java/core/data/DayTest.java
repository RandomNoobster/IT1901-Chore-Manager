package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that Days have the expected behavior.
 */
public class DayTest {
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

}
