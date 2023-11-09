package ui.viewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.data.Chore;
import core.data.Day;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ui.App;
import ui.dataAccessLayer.DataAccess;

/**
 * The DayView class represents a day in the calendar. It extends Button because it should be
 * clickable.
 */
public class DayView extends Button implements ViewInterface {

    private DataAccess dataAccess;
    private Day day;
    private VBox container = new VBox();
    private ScrollPane scrollContainer = new ScrollPane();
    private VBox vBoxContainer = new VBox();
    private Label pastDate;

    /**
     * A constructor for the DayView class. It creates and styles the FXML-element.
     *
     * @param day The day that the DayView should represent.
     */
    public DayView(Day day) {
        super();

        this.dataAccess = App.getDataAccess();

        // Go to chorecreation when button is pressed
        this.setOnAction(e -> App.setChoreCreationScene(day.getDate(), day.getDate()));

        // Add CSS
        this.container.getStyleClass().add("day-container");
        this.getStyleClass().addAll("bold", "dynamic-basic-shape", "on-hover-underline",
                "on-hover-background-blue", "background-blue", "white-text");
        this.scrollContainer.setContent(this.vBoxContainer);

        // Saving classes
        this.day = new Day(day);

        // If date = today, assign special class
        if (this.day.getDate().isEqual(LocalDate.now())) {
            this.container.getStyleClass().add("this-day");
        }

        // If date before today, make button a label instead
        if (this.day.getDate().isBefore(LocalDate.now())) {
            this.pastDate = new Label();
            this.pastDate.getStyleClass().addAll("dynamic-basic-shape", "background-blue");
            this.container.getChildren().add(this.pastDate);
        } else {
            super.setText("Add");
            this.container.getChildren().add(this);
        }

        this.container.getChildren().add(this.scrollContainer);

    }

    /**
     * Outputs the day that the DayView represents.
     *
     * @return The day that the DayView represents
     */
    public Day getDay() {
        return new Day(this.day);
    }

    /**
     * Outputs the container FXML-element of this DayView.
     *
     * @return The container FXML-element
     */
    @Override
    public Node getFxml() {
        return this.container;
    }

    /**
     * Outputs itself as a Button.
     *
     * @return The button FXML-element
     */
    public Button getButton() {
        return (Button) this;
    }

    /**
     * Updates the FXML of this DayView to include all active chores.
     */
    public void updateFxml(List<Chore> chores) {
        this.vBoxContainer.getChildren().clear();
        this.vBoxContainer.getStyleClass().addAll("distance-row");

        List<ChoreView> labels = new ArrayList<>();

        for (Chore chore : chores) {
            if (chore.getTimeFrom().equals(this.getDay().getDate())
                    && chore.getTimeTo().equals(this.getDay().getDate())) {
                ChoreView choreView = new ChoreView(chore, chore.getAssignedTo());
                labels.add(choreView);
            }
        }

        this.vBoxContainer.getChildren().addAll(labels);
    }

    /**
     * Updates the width of this and the parent FXML-elements.
     *
     * @param newWidth The new width of the FXML-elements
     */
    public void updateWidth(double newWidth) {
        if (this.pastDate != null) {
            this.pastDate.setMinWidth(newWidth);
            this.pastDate.setPrefWidth(newWidth);

        }

        for (Node node : Arrays.asList(this, this.container, this.scrollContainer,
                this.vBoxContainer)) {
            ((Region) node).setMinWidth(newWidth);
            ((Region) node).setPrefWidth(newWidth);
        }
        this.vBoxContainer.getChildren().forEach(l -> ((ChoreView) l).updateWidth(newWidth));

    }

    /**
     * Updates the height of this and the parent FXML-elements.
     *
     * @param newHeight The new height of the FXML-elements
     */
    public void updateHeight(double newHeight) {
        this.container.setMinHeight(newHeight - CSSGlobal.BUTTON_HEIGHT);
        this.container.setPrefHeight(newHeight - CSSGlobal.BUTTON_HEIGHT);
    }

}