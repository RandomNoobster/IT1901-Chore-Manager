package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Test that Weeks have the expected behavior.
 */
public class WeekTest extends BaseTestClass {

    private Week week;
    private final LocalDate date = LocalDate.of(2023, 9, 21);
    private final LocalDate startDate = LocalDate.of(2023, 9, 18);
    private static int WEEK_LENGTH = 7;

    /**
     * Before each test, create a new Week with some sample values.
     */
    @BeforeEach
    public void populateWeek() {
        this.week = new Week(this.date);
    }

    /**
     * Test that the constructor can construct a new object without throwing any errors.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Week(LocalDate.now()));
    }

    /**
     * Test that {@link Week#getDays} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetDays() {
        List<Day> daysInWeek = new ArrayList<Day>();
        for (int i = 0; i < WEEK_LENGTH; i++) {
            daysInWeek.add(new Day(this.startDate.plusDays(i)));
        }

        assertDoesNotThrow(() -> this.week.getDays());
        assertEquals(WEEK_LENGTH, this.week.getDays().size());
        assertEquals(daysInWeek, this.week.getDays());
    }

    /**
     * Test that {@link Week#getStartDate} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetStartDate() {
        assertDoesNotThrow(() -> this.week.getStartDate());
        assertEquals(this.startDate, this.week.getStartDate());
    }

    /**
     * Test that {@link Week#getWeekNumber} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetWeekNumber() {
        assertDoesNotThrow(() -> this.week.getWeekNumber());
        assertEquals((Integer) 38, this.week.getWeekNumber());
    }

    /**
     * Test that {@link Week#containsDay} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testContainsDay() {

        // All days in week should be contained in week
        for (int i = 0; i < 7; i++) {
            Day day = new Day(LocalDate.of(2023, 9, 18).plusDays(i));
            assertDoesNotThrow(() -> this.week.containsDay(day.getDate()));
            assertTrue(this.week.containsDay(day.getDate()));
        }

        // All days in the previous week should not be contained in week
        for (int i = 0; i < 7; i++) {
            Day day = new Day(LocalDate.of(2023, 9, 11).minusDays(7).plusDays(i));
            assertDoesNotThrow(() -> !this.week.containsDay(day.getDate()));
            assertTrue(!this.week.containsDay(day.getDate()));
        }
    }

}
