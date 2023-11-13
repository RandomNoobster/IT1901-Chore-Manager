package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.data.Chore;
import core.data.RestrictedCollective;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.dataAccessLayer.DataAccess;
import ui.viewClasses.CSSGlobal;
import ui.viewClasses.WeekView;

/**
 * The AppController class is the controller for the main view of the application.
 */
public class AppController {

    private DataAccess dataAccess;

    @FXML
    private VBox weekContainer;

    @FXML
    private VBox subScene;

    @FXML
    private GridPane mainGrid;

    @FXML
    private Button code;

    @FXML
    private Text collectiveName;

    private HBox topLabelContainer = new HBox();
    private List<WeekView> weeks = new ArrayList<>();
    private ScrollPane subWeekScrollContainer = new ScrollPane();
    private VBox subWeekContainer = new VBox();
    private static final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks before current week)
    private static final int NUM_WEEKS = 5; // Number of weeks to create

    private final List<String> WEEKDAYS = Arrays.asList("Week", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday");

    /**
     * A constructor for the AppController class.
     */
    public AppController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set data access layer
        this.dataAccess = App.getDataAccess();

        // Set collective name
        this.setCollectiveName();

        // Set top column that displays what each column means
        this.setTopColumn();

        // Create WeekView elements and add them to view
        this.weeks = this.createWeeks();

        // Make app responsive
        this.handleScreenResizing();

        // Update view
        this.updateFxml();

        this.weekContainer.getChildren().add(this.subWeekScrollContainer);
        this.subWeekScrollContainer.setContent(this.subWeekContainer);
    }

    /**
     * Sets the collective name to the name of the current collective.
     */
    private void setCollectiveName() {
        RestrictedCollective currentCollective = this.dataAccess.getCurrentCollective();
        this.code.setText("Code: " + currentCollective.getJoinCode());
        this.collectiveName.setText(currentCollective.getName());
    }

    /**
     * Sets the top column to the names of each weekday.
     */
    private void setTopColumn() {
        this.weekContainer.getChildren().add(this.topLabelContainer);
        for (String info : this.WEEKDAYS) {
            Label label = new Label(info);
            label.getStyleClass().addAll("static-basic-shape", "background-medium-blue", "bold");
            this.topLabelContainer.getChildren().add(label);
        }
    }

    /**
     * Makes the app responsive to changes in window size.
     */
    private void handleScreenResizing() {
        this.mainGrid.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            this.resizeWidth(width);

        });

        this.mainGrid.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            this.resizeHeight(height);
        });
    }

    /**
     * Resizes the width of the UI components based on the given width.
     *
     * @param width The new width.
     */
    private void resizeWidth(double width) {
        this.subScene.setMinWidth(width);
        this.subScene.setPrefWidth(width);

        this.weekContainer.setMinWidth(width);
        this.weekContainer.setPrefWidth(width);

        this.topLabelContainer.setMinWidth(width);
        this.topLabelContainer.setPrefWidth(width);

        this.topLabelContainer.getChildren().forEach(c -> ((Label) c)
                .setMinWidth((width - CSSGlobal.SCROLLBAR_WIDTH) / this.WEEKDAYS.size()));
        this.topLabelContainer.getChildren().forEach(c -> ((Label) c)
                .setPrefWidth((width - CSSGlobal.SCROLLBAR_WIDTH) / this.WEEKDAYS.size()));

        this.weeks.forEach(w -> w.updateWidth(width - CSSGlobal.SCROLLBAR_WIDTH));

    }

    /**
     * Resizes the height of the UI components based on the given height.
     *
     * @param height The new height.
     */
    private void resizeHeight(double height) {

        this.subScene.setMinHeight(height);
        this.subScene.setPrefHeight(height);

        this.weekContainer.setMinHeight(height - CSSGlobal.TOP_LABEL_HEIGHT);
        this.weekContainer.setPrefHeight(height - CSSGlobal.TOP_LABEL_HEIGHT);

        this.weeks.forEach(w -> w.updateHeight(CSSGlobal.WEEK_HEIGHT));

    }

    /**
     * Updates the FXML. Run this when updating the view with new content.
     */
    public void updateFxml() {
        List<Chore> chores = this.dataAccess.getChores();
        this.weeks.forEach(w -> w.updateFxml(chores));
    }

    /**
     * Creates the WeekView elements and adds them to the view.
     *
     * @return A list of the WeekViews.
     */
    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = SHIFT_WEEKS; i < NUM_WEEKS + SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * Week.WEEK_LENGTH))));
        }

        // Draw weeks
        for (WeekView week : weeks) {
            this.subWeekContainer.getChildren().add(week.getFxml());
        }
        return weeks;
    }

    /**
     * Switches to the leaderboard scene.
     */
    @FXML
    public void toLeaderboard() {
        App.switchScene("Leaderboard");
    }

    /**
     * Logs out the user and switches to the login scene.
     */
    @FXML
    public void toLogin() {
        this.dataAccess.logOut();
        App.switchScene("Login");
    }

    /**
     * Copies the collective code to the system clipboard and shows an information alert.
     */
    @FXML
    public void copyCode() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(this.dataAccess.getCurrentCollective().getJoinCode());
        clipboard.setContent(content);

        App.showAlert("Code copied", "Copied collective-code to clipboard", AlertType.INFORMATION);
    }
}
