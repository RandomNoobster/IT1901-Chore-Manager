package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTest {
    private Day day;
    private LocalDate date;

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

    @BeforeEach
    public void populateDay() {
        this.date = LocalDate.of(2020, 1, 1);
        this.day = new Day(this.date);
    }

    @Test
    public void testGetDate() {
        assertDoesNotThrow(() -> this.day.getDate());
        assertEquals(this.date, this.day.getDate());
    }

    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2020, 1, 2);
        assertDoesNotThrow(() -> this.day.setDate(newDate));
        assertEquals(newDate, this.day.getDate());
    }

}
