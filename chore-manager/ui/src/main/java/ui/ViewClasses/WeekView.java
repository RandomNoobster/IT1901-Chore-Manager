package ui.ViewClasses;

import java.time.LocalDate;

import core.Data.Day;
import core.Data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WeekView implements ViewInterface {

    private Week week;

    @FXML
    private HBox container = new HBox();

    public WeekView(Week week) {
        this.week = week;

        // Add week label to VBox
        Label weekNumberLabel = new Label(week.getWeekNumber().toString());
        weekNumberLabel.getStyleClass().add("weekNumberContainer");
        this.container.getChildren().add(weekNumberLabel);

        // Assign special class to week if week is current week
        if (week.containsDay(LocalDate.now())) {
            this.container.getStyleClass().add("thisWeek");
        } else {
            this.container.getStyleClass().add("pastWeek");
        }

        // Add day containers to VBox
        for (Day day : week.getDays()) {
            DayView dayView = new DayView(day);
            this.container.getChildren().add(dayView.getFxml());
        }
    }

    public Week getWeek() {
        return this.week;
    }

    @Override
    public HBox getFxml() {
        return this.container;
    }

}
