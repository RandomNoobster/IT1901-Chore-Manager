package core.Data;

import java.time.LocalDate;

public class Day {

    private LocalDate date;

    public Day(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Day)) {
            return false;
        }
        return this.date.equals(((Day) arg0).getDate());
    }

}
