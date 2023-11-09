package ui.viewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// import core.State;
import core.data.Chore;
import core.data.Day;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

    private static final int WEEK_CHORE_WEIGHT = 2;
    private static final int DAY_CHORE_WEIGHT = 5;

    // FXML hierarchy:
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

        // Set up hierarchy
        this.weekNumberContainer.getChildren().addAll(this.weekNumber, this.weekNumberButton);
        this.container.getChildren().addAll(this.weekNumberContainer, this.weekContainer);
        this.weekContainer.getChildren().addAll(this.dayContainer, this.scrollContainer);
        this.scrollContainer.setContent(this.weekChores);

        this.weekNumber.setText(week.getWeekNumber().toString());
        this.weekNumberButton.setText("Add");

        // Make weekNumberButton take you to chore creation
        this.weekNumberButton.setOnAction(e -> App.setChoreCreationScene("ChoreCreation",
                week.getStartDate(), week.getEndDate()));

        // Add day containers to VBox
        for (Day day : week.getDays()) {
            DayView dayView = new DayView(day);
            this.dayViews.add(dayView);
            this.dayContainer.getChildren().add(dayView.getFxml());
        }

        // Add styling
        this.addStyling();
    }

    private void addStyling() {
        this.weekNumber.getStyleClass().addAll("background-medium-blue", "bold", "white-text",
                "medium-font");
        this.weekNumberButton.getStyleClass().addAll("background-blue", "bold",
                "on-hover-underline", "on-hover-background-blue", "white-text", "medium-font");

        this.weekChores.getStyleClass().add("distance-row");

        // Assign special class to week if week is current week
        this.container.getStyleClass().add("week-container");
        if (this.week.containsDay(LocalDate.now())) {
            this.container.getStyleClass().add("this-week");
        } else {
            this.container.getStyleClass().add("past-week");
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

    private void updateWeekChoresFXML(List<Chore> chores) {
        this.weekChores.getChildren().clear();
        List<ChoreView> choreViews = new ArrayList<>();

        for (Chore chore : chores) {
            if (chore.getTimeFrom().equals(this.week.getStartDate())
                    && chore.getTimeTo().equals(this.week.getEndDate())) {

                ChoreView weekChore = new ChoreView(chore, chore.getAssignedTo(), true);
                choreViews.add(weekChore);
            }
        }

        this.weekChores.getChildren().addAll(choreViews);
    }

    /**
     * Updates the FXML-elements of the DayViews in the week.
     */
    public void updateFxml(List<Chore> chores) {
        this.getDayViews().forEach(d -> d.updateFxml(chores));
        this.updateWeekChoresFXML(chores);
    }

    /**
     * Set width of a region.
     *
     * @param region The region to set width of
     * @param width  The width to set
     */
    private void setMinAndPrefW(Region region, Double width) {
        region.setMinWidth(width);
        region.setPrefWidth(width);
    }

    /**
     * Set height of a region.
     *
     * @param region The region to set height of
     * @param width  The height to set
     */
    private void setMinAndPrefH(Region region, Double height) {
        region.setMinHeight(height);
        region.setPrefHeight(height);
    }

    /**
     * Updates the width of the week, the container and the DayViews.
     *
     * @param newWidth The new width of the week
     */
    public void updateWidth(double newWidth) {
        double sevenDayWidth = newWidth / this.COLUMN_COUNT * this.DAY_COUNT;

        this.setMinAndPrefW(this.container, newWidth);
        this.setMinAndPrefW(this.weekNumberContainer, newWidth / this.COLUMN_COUNT);
        this.setMinAndPrefW(this.weekNumber, newWidth / this.COLUMN_COUNT / 2);
        this.setMinAndPrefW(this.weekNumberButton, newWidth / this.COLUMN_COUNT / 2);
        this.setMinAndPrefW(this.weekContainer, sevenDayWidth);
        this.setMinAndPrefW(this.dayContainer, sevenDayWidth);
        this.setMinAndPrefW(this.scrollContainer, sevenDayWidth);
        this.setMinAndPrefW(this.weekChores, sevenDayWidth);

        for (DayView day : this.getDayViews()) {
            day.updateWidth(newWidth / this.COLUMN_COUNT);
        }

        this.weekChores.getChildren().forEach(l -> ((ChoreView) l).updateWidth(sevenDayWidth));
    }

    /**
     * Updates the height of the week and the container.
     *
     * @param newHeight The new height of the week
     */
    public void updateHeight(double newHeight) {
        int weekWeight = 0;
        if (this.weekChores.getChildren().size() > 0) {
            weekWeight = WEEK_CHORE_WEIGHT;
        }
        this.setMinAndPrefH(this.container, newHeight);
        this.setMinAndPrefH(this.weekNumberContainer, newHeight);
        this.setMinAndPrefH(this.weekNumberButton, newHeight);
        this.setMinAndPrefH(this.weekNumber, newHeight);
        this.setMinAndPrefH(this.weekContainer, newHeight);
        this.setMinAndPrefH(this.scrollContainer,
                newHeight * weekWeight / (weekWeight + DAY_CHORE_WEIGHT));
        this.setMinAndPrefH(this.dayContainer,
                newHeight * DAY_CHORE_WEIGHT / (weekWeight + DAY_CHORE_WEIGHT));

        for (DayView day : this.getDayViews()) {
            day.updateHeight(newHeight * DAY_CHORE_WEIGHT / (DAY_CHORE_WEIGHT + weekWeight));
        }

    }

}
