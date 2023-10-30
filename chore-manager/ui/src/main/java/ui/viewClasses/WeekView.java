package ui.viewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.State;
import core.data.Chore;
import core.data.Day;
import core.data.Person;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.App;

/**
 * The WeekView class represents a week in the calendar.
 */
public class WeekView implements ViewInterface {

    private Week week;
    private List<DayView> dayViews = new ArrayList<>();
    private final Integer COLUMN_COUNT = 8;
    private final Integer DAY_COUNT = 7;

    @FXML
    private HBox container = new HBox();
    private VBox weekContainer = new VBox();
    private HBox dayContainer = new HBox();
    private ScrollPane scrollContainer = new ScrollPane();
    private VBox weekChores = new VBox();

    private HBox weekNumberContainer = new HBox();
    private Label weekNumber = new Label();
    private Button weekNumberButton = new Button();

    // Structure:
    //
    // container
    // --- weekNumberContainer
    // --- --- weekNumber
    // --- --- weekNumberButton
    // --- weekContainer
    // --- --- dayContainer
    // --- --- --- dayView1
    // --- --- --- dayView2
    // --- --- --- ...
    // --- --- scrollContainer
    // --- --- --- weekChores
    // --- --- --- --- weekChore1
    // --- --- --- --- weekChore2
    // --- --- --- --- ...

    /**
     * A constructor for the WeekView class. It creates and styles the FXML-element.
     *
     * @param week The week to be represented by the WeekView.
     */
    public WeekView(Week week) {
        this.week = week;

        // Add week label to VBox
        this.weekNumberContainer.getChildren().addAll(this.weekNumber, this.weekNumberButton);
        this.weekNumber.setText(week.getWeekNumber().toString());
        this.weekNumberButton.setText("Add");
        this.weekNumber.getStyleClass().addAll("background-medium-blue", "bold", "white-text",
                "medium-font");
        this.weekNumberButton.getStyleClass().addAll("background-blue", "bold",
                "on-hover-underline", "on-hover-background-blue", "white-text", "medium-font");
        this.weekNumberButton.setOnAction(e -> App.setChoreCreationScene("ChoreCreation",
                week.getStartDate(), week.getEndDate()));

        this.container.getChildren().addAll(this.weekNumberContainer, this.weekContainer);
        this.weekContainer.getChildren().addAll(this.dayContainer, this.scrollContainer);
        this.scrollContainer.setContent(this.weekChores);

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
            this.dayContainer.getChildren().add(dayView.getFxml());
        }

        this.weekChores.getChildren().clear();
        this.weekChores.getStyleClass().add("distance-row");

        List<ChoreView> choreViews = new ArrayList<>();

        for (Person person : State.getInstance().getCurrentCollective().getPersonsList()) {
            for (Chore chore : person.getChores()) {
                if (chore.getTimeFrom().equals(this.week.getStartDate())
                        && chore.getTimeTo().equals(this.week.getEndDate())) {

                    ChoreView weekChore = new ChoreView(chore, person, true);
                    choreViews.add(weekChore);
                }
            }
        }
        this.weekChores.getChildren().addAll(choreViews);
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
        this.container.setMinWidth(newWidth);
        this.container.setPrefWidth(newWidth);

        this.weekNumberContainer.setMinWidth(newWidth / this.COLUMN_COUNT);
        this.weekNumberContainer.setPrefWidth(newWidth / this.COLUMN_COUNT);

        this.weekNumber.setMinWidth(newWidth / this.COLUMN_COUNT / 2);
        this.weekNumber.setPrefWidth(newWidth / this.COLUMN_COUNT / 2);

        this.weekNumberButton.setMinWidth(newWidth / this.COLUMN_COUNT / 2);
        this.weekNumberButton.setPrefWidth(newWidth / this.COLUMN_COUNT / 2);

        for (DayView day : this.getDayViews()) {
            day.updateWidth(newWidth / this.COLUMN_COUNT);
        }

        double sevenDayWidth = newWidth / this.COLUMN_COUNT * this.DAY_COUNT;

        this.weekContainer.setMinWidth(sevenDayWidth);
        this.weekContainer.setPrefWidth(sevenDayWidth);

        this.dayContainer.setMinWidth(sevenDayWidth);
        this.dayContainer.setPrefWidth(sevenDayWidth);

        this.scrollContainer.setMinWidth(sevenDayWidth);
        this.scrollContainer.setPrefWidth(sevenDayWidth);

        this.weekChores.setMinWidth(sevenDayWidth);
        this.weekChores.setPrefWidth(sevenDayWidth);

        this.weekChores.getChildren().forEach(l -> ((ChoreView) l).updateWidth(sevenDayWidth));
    }

    /**
     * Updates the height of the week and the container.
     *
     * @param newHeight The new height of the week
     */
    public void updateHeight(double newHeight) {
        this.container.setMinHeight(newHeight);
        this.container.setPrefHeight(newHeight);

        this.weekNumberContainer.setMinHeight(newHeight);
        this.weekNumberContainer.setPrefHeight(newHeight);

        this.weekNumberButton.setMinHeight(newHeight);
        this.weekNumberButton.setPrefHeight(newHeight);

        this.weekNumber.setMinHeight(newHeight);
        this.weekNumber.setPrefHeight(newHeight);

        this.weekContainer.setMinHeight(newHeight);
        this.weekContainer.setPrefHeight(newHeight);

        int zeroHeight = 0;
        if (this.weekChores.getChildren().size() > 0) {
            zeroHeight = 1;
        }

        this.scrollContainer.setPrefHeight(newHeight * zeroHeight / 7 * 2);
        this.scrollContainer.setMinHeight(newHeight * zeroHeight / 7 * 2);

        this.dayContainer.setMinHeight(newHeight / (5 + zeroHeight * 2) * 5);
        this.dayContainer.setPrefHeight(newHeight / (5 + zeroHeight * 2) * 5);
        for (DayView day : this.getDayViews()) {
            day.updateHeight(newHeight / (5 + zeroHeight * 2) * 5);
        }

    }

}
