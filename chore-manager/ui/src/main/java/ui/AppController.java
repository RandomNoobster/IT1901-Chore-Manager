package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.State;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.viewClasses.DayView;
import ui.viewClasses.WeekView;

/**
 * The AppController class is the controller for the main view of the application.
 */
public class AppController {

    @FXML
    private VBox weekContainer;

    @FXML
    private GridPane scene;

    @FXML
    private Button code;

    @FXML
    private Text collectiveName;

    private HBox topLabelContainer = new HBox();
    private List<WeekView> weeks = new ArrayList<>();
    private static final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks
                                               // before
    // current week)
    private static final int NUM_WEEKS = 4; // Number of weeks to create
    private final List<String> WEEKDAYS = Arrays.asList("Week", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday");

    /**
     * A constructor for the AppController class.
     */
    public AppController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    public void initialize() {

        this.code.setText("Code: " + State.getInstance().getCurrentCollective().getJoinCode());
        this.collectiveName.setText(State.getInstance().getCurrentCollective().getName());

        // Set top column that displays what each column means
        this.setTopColumn();

        // Create WeekView elements and add them to view
        this.weeks = this.createWeeks();

        // Make buttons run function on click
        this.addDayActions();

        // Make app responsive
        this.handleScreenResizing();

        // Update view
        this.updateFxml();

        // Temporary solution to update width and height
        ScheduledExecutorService runCommandDelayed = Executors.newScheduledThreadPool(1);
        runCommandDelayed.submit(() -> {
        });
        runCommandDelayed.schedule(() -> {
            this.resizeWidth(this.scene.getWidth());
            this.resizeHeight(this.scene.getHeight());
        }, 1000, TimeUnit.MILLISECONDS);
        runCommandDelayed.shutdown();

    }

    /**
     * Makes the DayView buttons create a new chore when clicked.
     */
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

    /**
     * Sets the top column to the names of each weekday.
     */
    private void setTopColumn() {
        this.weekContainer.getChildren().add(this.topLabelContainer);
        for (String info : this.WEEKDAYS) {
            Label label = new Label(info);
            label.getStyleClass().addAll("static-basic-shape", "background-yellow", "bold");
            this.topLabelContainer.getChildren().add(label);
        }
    }

    /**
     * Makes the app responsive to changes in window size.
     */
    private void handleScreenResizing() {
        this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            this.resizeWidth(width);

        });

        this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            this.resizeHeight(height);
        });
    }

    private void resizeWidth(double width) {
        this.weekContainer.setPrefWidth(width);
        this.weeks.forEach(w -> w.updateWidth(width));
        this.topLabelContainer.setPrefWidth(width);
        this.topLabelContainer.getChildren().forEach(c -> ((Label) c).setPrefWidth(width / 8));
    }

    private void resizeHeight(double height) {
        double topLabelHeight = 30;

        this.weekContainer.setPrefHeight(height);
        this.weeks.forEach(w -> w.updateHeight((height - topLabelHeight) / this.NUM_WEEKS));
    }

    public void updateFxml() {
        this.weeks.forEach(w -> w.updateFxml());
    }

    /**
     * Creates the WeekView elements and adds them to the view.
     *
     * @return A list of the WeekViews
     */
    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = SHIFT_WEEKS; i < NUM_WEEKS + SHIFT_WEEKS; i++) {
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
        App.setChoreCreationScene("ChoreCreation", dateFrom, dateTo);
    }

    @FXML
    public void toLeaderboard() {
        App.switchScene("Leaderboard");
    }

    @FXML
    public void toLogin() {
        State.getInstance().logOutUser();
        App.switchScene("Login");
    }

    @FXML
    public void copyCode() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(State.getInstance().getCurrentCollective().getJoinCode());
        clipboard.setContent(content);
    }
}
