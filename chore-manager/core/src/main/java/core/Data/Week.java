package core.Data;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Week {

    public static int WEEK_LENGTH = 7;

    private LocalDate startDate;
    private List<Day> days = new ArrayList<>();

    public Week(LocalDate dateInWeek) {

        // Subtract 1 to make days go from 0-6 instead of 1-7
        Integer dayNumber = dateInWeek.getDayOfWeek().getValue() - 1;
        this.startDate = dateInWeek.minusDays(dayNumber);

        for (int i = 0; i < WEEK_LENGTH; i++) {
            Day tempDay = new Day(this.startDate.plusDays(i));
            this.days.add(tempDay);
        }
    }

    public List<Day> getDays() {
        return new ArrayList<Day>(this.days);
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Integer getWeekNumber() {
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = this.getStartDate().get(weekOfYear);
        return weekNumber;
    }

    public boolean containsDay(LocalDate date) {
        return this.days.stream().anyMatch(day -> day.getDate().isEqual(date));
    }

}
