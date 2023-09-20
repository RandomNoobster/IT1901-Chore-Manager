package ui.ViewClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Chore;
import core.Data.Day;
import core.Data.Person;
import core.FileHandling.Storage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DayView extends Button implements ViewInterface {

    private Day day;
    private VBox realContainer = new VBox();
    private ScrollPane scrollContainer = new ScrollPane();
    private VBox vBoxContainer = new VBox();

    // @FXML
    // private Button container = new Button();

    public DayView(Day day) {
        super();

        this.scrollContainer.setContent(this.vBoxContainer);
        super.setText("Add chore");

        this.realContainer.getChildren().addAll(this, this.scrollContainer);

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

        for (Person person : Storage.getPersons()) {
            for (Chore chore : person.getChores()) {
                if (chore.getTimeFrom().equals(this.getDay().getDate())) {
                    labels.add(new Label(chore.getName()));
                }
            }
        }

        this.vBoxContainer.getChildren().addAll(labels);
    }

}