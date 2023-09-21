package core.Data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeekTest {

    private Week week;
    private final LocalDate date = LocalDate.of(2023, 9, 21);
    private final LocalDate startDate = LocalDate.of(2023, 9, 18);
    private static int WEEK_LENGTH = 7;

    @BeforeEach
    public void populateWeek() {
        this.week = new Week(this.date);
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Week(LocalDate.now()));
    }

    @Test
    public void testGetDays() {
        List<Day> daysInWeek = new ArrayList<Day>();
        for (int i = 0; i < WEEK_LENGTH; i++) {
            daysInWeek.add(new Day(this.startDate.plusDays(i)));
        }

        assertEquals(WEEK_LENGTH, this.week.getDays().size());
        assertEquals(daysInWeek, this.week.getDays());
    }

    @Test
    public void testGetStartDate() {
        assertEquals(this.startDate, this.week.getStartDate());
    }

    @Test
    public void testGetWeekNumber() {
        assertEquals((Integer) 38, this.week.getWeekNumber());
    }

    @Test
    public void testContainsDay() {

        // All days in week should be contained in week
        for (int i = 0; i < 7; i++) {
            Day day = new Day(LocalDate.of(2023, 9, 18).plusDays(i));
            assertTrue(this.week.containsDay(day.getDate()));
        }

        // All days in the previous week should not be contained in week
        for (int i = 0; i < 7; i++) {
            Day day = new Day(LocalDate.of(2023, 9, 11).minusDays(7).plusDays(i));
            assertTrue(!this.week.containsDay(day.getDate()));
        }
    }

}
