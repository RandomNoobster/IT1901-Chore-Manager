package ui;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Week {

    private LocalDate startDay;
    private List<Day> days = new ArrayList<>();

    @FXML
    private HBox container = new HBox();

    public Week(LocalDate dateInWeek) {

        // Subtract 1 to make days go from 0-6 instead of 0-7
        Integer dayNr = dateInWeek.getDayOfWeek().getValue() - 1;
        this.startDay = dateInWeek.minusDays(dayNr);

        // Add week label to VBox
        Label weekNrLabel = new Label(this.getWeekNr().toString());
        weekNrLabel.getStyleClass().add("dayContainer");
        this.container.getChildren().add(weekNrLabel);

        // Assign special class to week if week is current week
        if (dateInWeek.isEqual(LocalDate.now())) {
            this.container.getStyleClass().add("thisWeek");
        } else {
            this.container.getStyleClass().add("pastWeek");
        }

        // Add day containers to VBox
        for (int i = 0; i < 7; i++) {
            Day tempDay = new Day(this.startDay.plusDays(i));
            this.days.add(tempDay);
            this.container.getChildren().add(tempDay.getFxml());
        }

    }

    private Integer getWeekNr() {
        // It works
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = this.getStartDay().get(woy);
        return weekNumber;
    }

    private LocalDate getStartDay() {
        return this.startDay;
    }

    public HBox getFxml() {
        return this.container;
    }

}
