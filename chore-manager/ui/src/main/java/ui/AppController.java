package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Data.Week;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.ViewClasses.DayView;
import ui.ViewClasses.WeekView;

public class AppController {

    @FXML
    private VBox weekContainer;

    @FXML
    private GridPane scene;

    private Stage stage;

    private HBox topLabelContainer = new HBox();
    private List<WeekView> weeks = new ArrayList<>();
    private final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks before current week)
    private final int NUM_WEEKS = 4; // Number of weeks to create

    public AppController() {
    }

    @FXML
    public void initialize() {

        // Set top column that displays what each column means
        this.setTopColumn();

        // Create weekview elements and add them to view
        this.weeks = this.createWeeks();

        // Make buttons run function on click
        this.addDayActions();

        // Make app responsive
        this.handleScreenResizing();

        // Update view
        this.updateFxml();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Make buttons run function
    private void addDayActions() {
        for (WeekView week : this.weeks) {
            List<DayView> days = week.getDayViews();
            for (DayView day : days) {
                day.getButton().setOnAction(e -> {
                    DayView target = (DayView) e.getTarget();
                    this.switchToChoreCreation(target.getDay().getDate(),
                            target.getDay().getDate());
                });
            }
        }
    }

    // Set top column that displays what each column means
    private void setTopColumn() {
        this.weekContainer.getChildren().add(this.topLabelContainer);
        for (String info : Arrays.asList("Week", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday")) {
            Label label = new Label(info);
            label.getStyleClass().addAll("label", "weekLabelsColor", "header");
            this.topLabelContainer.getChildren().add(label);
        }
    }

    // Make app responsive
    private void handleScreenResizing() {
        this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            this.weekContainer.setPrefWidth(width);
            this.weeks.forEach(w -> w.updateWidth(width));
            this.topLabelContainer.setPrefWidth(width);
            this.topLabelContainer.getChildren().forEach(c -> ((Label) c).setPrefWidth(width / 8));

        });

        this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            double topLabelHeight = 30;

            this.weekContainer.setPrefHeight(height);
            this.weeks.forEach(w -> w.updateHeight((height - topLabelHeight) / this.NUM_WEEKS));
        });
    }

    public void updateFxml() {
        this.weeks.forEach(w -> w.updateFxml());
    }

    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = this.SHIFT_WEEKS; i < this.NUM_WEEKS + this.SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * Week.WEEK_LENGTH))));
        }

        // Draw weeks
        for (WeekView week : weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }
        return weeks;
    }

    @FXML
    private void switchToChoreCreation(LocalDate dateFrom, LocalDate dateTo) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ChoreCreation.fxml"));
            Scene choreCreationScene = new Scene(loader.load());
            ChoreCreationController choreCreationController = loader.getController();
            choreCreationController.passData(dateFrom, dateTo, this.stage, this.stage.getScene(),
                    this);
            choreCreationScene.getStylesheets()
                    .add(this.getClass().getResource("Style.css").toExternalForm());

            this.stage.setScene(choreCreationScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
