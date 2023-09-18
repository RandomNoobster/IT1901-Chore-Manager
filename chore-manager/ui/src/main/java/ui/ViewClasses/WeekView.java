package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Day;
import core.Data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ui.AppController;

public class WeekView implements ViewInterface {

    private Week week;
    private List<DayView> dayViews = new ArrayList<>();

    @FXML
    private HBox container = new HBox();

    public WeekView(Week week, AppController controller) {
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
            DayView dayView = new DayView(day, controller);
            this.dayViews.add(dayView);
            this.container.getChildren().add(dayView.getFxml());
        }
    }

    public Week getWeek() {
        return this.week;
    }

    public List<DayView> getDayViews() {
        return this.dayViews;
    }

    @Override
    public HBox getFxml() {
        return this.container;
    }

    public void updateFxml() {
        this.getDayViews().forEach(d -> d.updateFxml());
    }

}
