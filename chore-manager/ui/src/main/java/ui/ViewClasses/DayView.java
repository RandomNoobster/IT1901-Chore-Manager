package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Data.Chore;
import core.Data.ContrastColor;
import core.Data.Day;
import core.Data.Person;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import persistence.FileHandling.Storage;

/**
 * The DayView class represents a day in the calendar. It extends Button because
 * it should be clickable.
 */
public class DayView extends Button implements ViewInterface {

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

        // Add CSS
        this.container.getStyleClass().add("dayContainer");
        this.vBoxContainer.getStyleClass().add("list-item-container");
        this.getStyleClass().addAll("header", "button", "hoverClass");

        this.scrollContainer.setContent(this.vBoxContainer);

        // Saving classes
        this.day = day;

        this.updateFxml();

        // If date = today, assign special class
        if (day.getDate().isEqual(LocalDate.now())) {
            this.container.getStyleClass().add("thisDay");
        }

        // If date before today, make button a label instead
        if (day.getDate().isBefore(LocalDate.now())) {
            this.pastDate = new Label();
            this.pastDate.getStyleClass().add("label");
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
        return this.day;
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
    public void updateFxml() {
        this.vBoxContainer.getChildren().clear();

        List<Label> labels = new ArrayList<>();

        for (Person person : Storage.getInstance().getPersonsList()) {
            for (Chore chore : person.getChores()) {
                if (chore.getTimeFrom().equals(this.getDay().getDate())) {
                    Label choreLabel = new Label(chore.getName());
                    choreLabel.getStyleClass().add("list-item");

                    if (ContrastColor.blackText(chore.getColor())) {
                        choreLabel.setTextFill(Color.WHITE);
                    } else {
                        choreLabel.setTextFill(Color.BLACK);
                    }

                    choreLabel.setStyle("-fx-background-color: " + chore.getColor() + ";");

                    labels.add(choreLabel);

                }
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
            this.pastDate.setPrefWidth(newWidth);

        }
        for (Node node : Arrays.asList(this, this.container, this.scrollContainer,
                this.vBoxContainer)) {
            ((Region) node).setPrefWidth(newWidth);
        }
    }

}