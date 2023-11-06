package core.data;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The Week class contains logic for a week. It stores information about the days in the week.
 */
public class Week {

    public static final int WEEK_LENGTH = 7;

    private LocalDate startDate;
    private List<Day> days = new ArrayList<>();

    /**
     * A constructor for the Week class that initializes the state of the object.
     *
     * @param dateInWeek The date whose week is to be created
     */
    public Week(LocalDate dateInWeek) {

        // Subtract 1 to make days go from 0-6 instead of 1-7
        Integer dayNumber = dateInWeek.getDayOfWeek().getValue() - 1;
        this.startDate = dateInWeek.minusDays(dayNumber);

        for (int i = 0; i < WEEK_LENGTH; i++) {
            Day tempDay = new Day(this.startDate.plusDays(i));
            this.days.add(tempDay);
        }
    }

    /**
     * Outputs a list of the days in the week.
     *
     * @return A list of the days in the week
     */
    public List<Day> getDays() {
        return new ArrayList<Day>(this.days);
    }

    /**
     * Outputs the start date of the week.
     *
     * @return The start date of the week
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Outputs the end date of the week.
     *
     * @return The end date of the week
     */
    public LocalDate getEndDate() {
        return this.startDate.plusDays(7);
    }

    /**
     * Outputs the week number (0-52).
     *
     * @return The number of the week
     */
    public Integer getWeekNumber() {
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = this.getStartDate().get(weekOfYear);
        return weekNumber;
    }

    /**
     * Outputs a boolean indicating wether or not the week contains a date.
     *
     * @param date The date to check
     * @return If the week contains the date
     */
    public boolean containsDay(LocalDate date) {
        return this.days.stream().anyMatch(day -> day.getDate().isEqual(date));
    }

}
