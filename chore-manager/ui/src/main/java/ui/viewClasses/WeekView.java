package ui.viewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.data.Day;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The WeekView class represents a week in the calendar.
 */
public class WeekView implements ViewInterface {

    private Week week;
    private List<DayView> dayViews = new ArrayList<>();
    private final Integer COLUMN_COUNT = 8;

    @FXML
    private HBox container = new HBox();
    private Label weekNumberLabel;

    /**
     * A constructor for the WeekView class. It creates and styles the FXML-element.
     *
     * @param week The week to be represented by the WeekView.
     */
    public WeekView(Week week) {
        this.week = week;

        // Add week label to VBox
        this.weekNumberLabel = new Label(week.getWeekNumber().toString());
        this.weekNumberLabel.getStyleClass().addAll("dynamic-week-number", "background-blue",
                "bold");

        this.container.getChildren().add(this.weekNumberLabel);

        // Assign special class to week if week is current week
        this.container.getStyleClass().add("week-container");
        if (week.containsDay(LocalDate.now())) {
            this.container.getStyleClass().add("this-week");
        } else {
            this.container.getStyleClass().add("past-week");
        }

        // Add day containers to VBox
        for (Day day : week.getDays()) {
            DayView dayView = new DayView(day);
            this.dayViews.add(dayView);
            this.container.getChildren().add(dayView.getFxml());
        }
    }

    /**
     * Outputs the week.
     *
     * @return The week
     */
    public Week getWeek() {
        return this.week;
    }

    /**
     * Outputs a list of the DayViews in the week.
     *
     * @return A list of the DayViews in the week
     */
    public List<DayView> getDayViews() {
        return new ArrayList<>(this.dayViews);
    }

    /**
     * Outputs the container FXML-element.
     *
     * @return The container FXML-element
     */
    @Override
    public HBox getFxml() {
        return new HBox(this.container);
    }

    /**
     * Updates the FXML-elements of the DayViews in the week.
     */
    public void updateFxml() {
        this.getDayViews().forEach(d -> d.updateFxml());
    }

    /**
     * Updates the width of the week, the container and the DayViews.
     *
     * @param newWidth The new width of the week
     */
    public void updateWidth(double newWidth) {
        this.container.setPrefWidth(newWidth);
        this.weekNumberLabel.setPrefWidth(newWidth / this.COLUMN_COUNT);

        for (DayView day : this.getDayViews()) {
            day.updateWidth(newWidth / this.COLUMN_COUNT);
        }
    }

    /**
     * Updates the height of the week and the container.
     *
     * @param newHeight The new height of the week
     */
    public void updateHeight(double newHeight) {
        this.container.setPrefHeight(newHeight);
        this.weekNumberLabel.setPrefHeight(newHeight);
    }

}
