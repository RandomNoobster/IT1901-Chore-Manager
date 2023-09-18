package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Chore;
import core.Data.Day;
import core.Data.Person;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ui.AppController;

public class DayView extends Button implements ViewInterface {

    private Day day;
    private AppController controller;
    private VBox realContainer = new VBox();
    private ScrollPane scrollContainer = new ScrollPane();
    private VBox vBoxContainer = new VBox();

    // @FXML
    // private Button container = new Button();

    public DayView(Day day, AppController controller) {
        super();

        this.scrollContainer.setContent(this.vBoxContainer);
        super.setText("Add chore");

        this.realContainer.getChildren().addAll(this, this.scrollContainer);

        this.controller = controller;
        this.day = day;
        this.updateFxml();

        // If date = today, assign special class
        if (day.getDate().isEqual(LocalDate.now())) {
            // add special css here
        }
    }

    public Day getDay() {
        return this.day;
    }

    @Override
    public Node getFxml() {
        return this.realContainer;
    }

    public Button getButton() {
        return (Button) this;
    }

    public void updateFxml() {
        this.vBoxContainer.getChildren().clear();

        List<Label> labels = new ArrayList<>();

        for (Person person : this.controller.getPeople()) {
            for (Chore chore : person.getChores()) {
                if (chore.getTimeFrom().equals(this.getDay().getDate())) {
                    labels.add(new Label(chore.getName()));
                }
            }
        }

        this.vBoxContainer.getChildren().addAll(labels);
    }

}

// @FXML
// private Button container = new Button();

// public Day(LocalDate date) {
// this.date = date;
// this.container.setText(date.getDayOfWeek().toString() + " - " +
// Integer.toString(date.getDayOfMonth()));
// this.container.getStyleClass().add("dayContainer");

// // If date = today, assign special class
// if (date.isEqual(LocalDate.now())) {
// this.container.getStyleClass().add("todayContainer");

// }
// }

// public Button getFxml() {
// return this.container;
// }