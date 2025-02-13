package core.data;

import java.time.LocalDate;

/**
 * The Day class represents a day in the year. It stores information about the date.
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

    public Day(Day day) {
        this.date = day.date;
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

    /**
     * Outputs the hashCode of the date.
     *
     * @return The hashCode of the date
     */
    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

}
