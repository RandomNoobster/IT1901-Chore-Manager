package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Day;
import core.Data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WeekView implements ViewInterface {

    private Week week;
    private List<DayView> dayViews = new ArrayList<>();
    private Integer COLUMN_COUNT = 8;

    @FXML
    private HBox container = new HBox();
    private Label weekNumberLabel;

    public WeekView(Week week) {
        this.week = week;

        // Add week label to VBox
        this.weekNumberLabel = new Label(week.getWeekNumber().toString());
        this.weekNumberLabel.getStyleClass().addAll("weekNumberContainer", "header");

        this.container.getChildren().add(this.weekNumberLabel);

        // Assign special class to week if week is current week
        this.container.getStyleClass().add("weekContainer");
        if (week.containsDay(LocalDate.now())) {
            this.container.getStyleClass().add("thisWeek");
        } else {
            this.container.getStyleClass().add("pastWeek");
        }

        // Add day containers to VBox
        for (Day day : week.getDays()) {
            DayView dayView = new DayView(day);
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

    public void updateWidth(double newWidth) {
        this.container.setPrefWidth(newWidth);
        this.weekNumberLabel.setPrefWidth(newWidth / 8);

        for (DayView day : this.getDayViews()) {
            day.updateWidth(newWidth / this.COLUMN_COUNT);
        }
    }

    public void updateHeight(double newHeight) {
        this.container.setPrefHeight(newHeight);
        this.weekNumberLabel.setPrefHeight(newHeight);
    }

}
