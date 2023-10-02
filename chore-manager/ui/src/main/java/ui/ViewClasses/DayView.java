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

public class DayView extends Button implements ViewInterface {

    private Day day;
    private VBox container = new VBox();
    private ScrollPane scrollContainer = new ScrollPane();
    private VBox vBoxContainer = new VBox();
    private Label pastDate;

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

    public Day getDay() {
        return this.day;
    }

    @Override
    public Node getFxml() {
        return this.container;
    }

    public Button getButton() {
        return (Button) this;
    }

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

    public void updateWidth(double newWidth) {
        if (this.pastDate != null) {
            this.pastDate.setPrefWidth(newWidth);

        }
        for (Node node : Arrays.asList(this, this.container, this.scrollContainer, this.vBoxContainer)) {
            ((Region) node).setPrefWidth(newWidth);
        }
    }

}