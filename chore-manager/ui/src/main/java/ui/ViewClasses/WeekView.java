package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Chore;
import core.Data.Day;
import core.Data.Person;
import core.Data.Week;
import core.FileHandling.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeekView implements ViewInterface {

    private Week week;
    private List<DayView> dayViews = new ArrayList<>();
    private Integer COLUMN_COUNT = 8;

    @FXML
    private HBox container = new HBox();
    private WeekButton weekButton;
    private VBox weekAndDayContainer = new VBox();
    private ScrollPane scroll = new ScrollPane();
    private HBox dayContainer = new HBox();
    private VBox weekContainer = new VBox();
    private Integer weekChores = 0;

    public WeekView(Week week) {
        this.week = week;

        // Add week label to VBox
        this.weekButton = new WeekButton(this.week);

        this.container.getChildren().addAll(this.weekButton, this.weekAndDayContainer);
        this.scroll.setContent(this.weekContainer);
        this.weekAndDayContainer.getChildren().addAll(this.dayContainer, this.scroll);

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
            this.dayContainer.getChildren().add(dayView.getFxml());
        }

        this.updateFxml();

    }

    public WeekButton getWeekButton() {
        return this.weekButton;
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
        for (Person person : Storage.getPersons()) {
            for (Chore chore : person.getChores()) {
                if (chore.getTimeFrom().isEqual(this.week.getStartDate())
                        && chore.getTimeTo().isEqual(this.week.getStartDate().plusDays(6))) {
                    Label weekChore = new Label(chore.getName());
                    this.weekContainer.getChildren().add(weekChore);
                }
            }
        }
    }

    public void updateWidth(double newWidth) {
        this.container.setPrefWidth(newWidth);
        this.weekButton.setPrefWidth(newWidth / 8);

        for (DayView day : this.getDayViews()) {
            day.updateWidth(newWidth / this.COLUMN_COUNT);
        }
    }

    public void updateHeight(double newHeight) {
        this.container.setPrefHeight(newHeight);
        this.weekButton.setPrefHeight(newHeight);
    }

}
