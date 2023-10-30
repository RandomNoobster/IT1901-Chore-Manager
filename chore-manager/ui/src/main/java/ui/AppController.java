package ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.State;
import core.data.Week;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import persistence.fileHandling.EnvironmentConfigurator;
import ui.dataAccessLayer.DataAccess;
import ui.dataAccessLayer.RemoteDataAccess;
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
     * Sets the data access layer for the controller.
     */
    private void setDataAccess() {
        EnvironmentConfigurator configurator = new EnvironmentConfigurator();
        URI apiBaseEndpoint = configurator.getAPIBaseEndpoint();
        System.out.println(apiBaseEndpoint);
        if (apiBaseEndpoint != null) {
            this.dataAccess = new RemoteDataAccess(apiBaseEndpoint);
        } else {
            // Use direct data access here
            throw new RuntimeException("Could not find API base endpoint");
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    public void initialize() {
        // Set data access layer
        this.setDataAccess();
        this.dataAccess.getCollectives();
        System.out.println(this.dataAccess.getCollectives());
        System.out.println(this.dataAccess.getCollectives().size());

        this.code.setText("Code: " + State.getInstance().getCurrentCollective().getJoinCode());
        this.collectiveName.setText(State.getInstance().getCurrentCollective().getName());

        // Set top column that displays what each column means
        this.setTopColumn();

        // Create WeekView elements and add them to view
        this.weeks = this.createWeeks();

        // Make app responsive
        this.handleScreenResizing();

        // Update view
        this.updateFxml();
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
        this.mainGrid.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            this.resizeWidth(width);

        });

        this.mainGrid.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            this.resizeHeight(height);
        });
    }

    private void resizeWidth(double width) {
        this.subScene.setMinWidth(width);
        this.subScene.setPrefWidth(width);

        this.weekContainer.setMinWidth(width);
        this.weekContainer.setPrefWidth(width);

        this.topLabelContainer.setMinWidth(width);
        this.topLabelContainer.setPrefWidth(width);

        this.topLabelContainer.getChildren().forEach(c -> ((Label) c).setMinWidth(width / 8));
        this.topLabelContainer.getChildren().forEach(c -> ((Label) c).setPrefWidth(width / 8));

        this.weeks.forEach(w -> w.updateWidth(width));

    }

    private void resizeHeight(double height) {
        double topLabelHeight = 30;
        double topToolbar = 50;

        this.subScene.setMinHeight(height);
        this.subScene.setPrefHeight(height);

        this.weekContainer.setMinHeight(height - topToolbar);
        this.weekContainer.setPrefHeight(height - topToolbar);

        this.weeks.forEach(
                w -> w.updateHeight((height - topLabelHeight - topToolbar) / this.NUM_WEEKS));

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

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Code copied");
        alert.setHeaderText("Copied collective-code to clipboard");
        alert.show();
    }
}
