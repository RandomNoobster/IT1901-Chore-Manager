package core.Data;

import java.time.LocalDate;

/**
 * The Day class represents a day in the calendar.
 * It stores information about the date.
 */
public class Day {

    private LocalDate date;

    /**
     * A constructor for the Day class that initializes the state of the object.
     *
     * @param date The date of the day
     */
    public Day(LocalDate date) {
        this.date = date;
    }

    /**
     * Outputs the date of the day.
     *
     * @return The date of the day
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Sets the date of the day.
     *
     * @param date The date of the day
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Outputs a boolean indicating wether or not the objects have the same date.
     *
     * @return If the dates are equal
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Day)) {
            return false;
        }
        return this.date.equals(((Day) arg0).getDate());
    }

}
