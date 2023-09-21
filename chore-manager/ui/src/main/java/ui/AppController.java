package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;
import core.Data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.ViewClasses.DayView;
import ui.ViewClasses.WeekView;

public class AppController {

    @FXML
    private VBox weekContainer;

    @FXML
    private GridPane scene;

    private List<WeekView> weeks = new ArrayList<>();
    private final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks before current week)
    private final int NUM_WEEKS = 3; // Number of weeks to create

    // Use this for future functionality for first iteration
    public Person TEMP_PERSON = new Person("TEST_PERS");

    public AppController() {

    }

    @FXML
    protected void initialize() {
        // Set top column that displays what each column means
        HBox topLabelContainer = new HBox();
        this.weekContainer.getChildren().add(topLabelContainer);
        for (String info : Arrays.asList("Week", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                "Sunday")) {
            Label label = new Label(info);
            label.getStyleClass().add("label");
            topLabelContainer.getChildren().add(label);
        }

        // Create weekview elements and add them to view
        this.weeks = this.createWeeks();

        for (WeekView week : this.weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }

        // Make buttons run function
        for (WeekView week : this.weeks) {
            List<DayView> days = week.getDayViews();
            for (DayView day : days) {
                day.getButton().setOnAction(e -> {
                    DayView target = (DayView) e.getTarget();
                    this.createChore(target.getDay().getDate());
                });
            }
        }

        // Handle screen resizing
        this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            this.weekContainer.setPrefWidth(width);
            this.weeks.forEach(w -> w.updateWidth(width));
            topLabelContainer.setPrefWidth(width);
            topLabelContainer.getChildren().forEach(c -> ((Label) c).setPrefWidth(width / 8));

        });

        this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            double topLabelHeight = 30;

            this.weekContainer.setPrefHeight(height);
            this.weeks.forEach(w -> w.updateHeight((height - topLabelHeight) / this.NUM_WEEKS));
        });

    }

    public List<Person> getPeople() {
        return new ArrayList<Person>() {
            {
                this.add(AppController.this.TEMP_PERSON);
            }
        };
    }

    public void createChore(LocalDate date) {
        Chore chore = new Chore("Reminder", date, date, false, 10);
        this.TEMP_PERSON.addChore(chore);
        System.out.println(this.TEMP_PERSON.getChores());
        this.updateFxml();
    }

    public void updateFxml() {
        this.weeks.forEach(w -> w.updateFxml());
    }

    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = this.SHIFT_WEEKS; i < this.NUM_WEEKS + this.SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * 7)), this));
        }
        return weeks;
    }

}
