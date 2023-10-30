package ui.viewClasses;

import java.time.LocalDate;

import core.data.Chore;
import core.data.ContrastColor;
import core.data.Person;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ui.App;

/**
 * The ChoreView class represents a chore in the calendar.
 */
public class ChoreView extends VBox implements ViewInterface {
    private Label assignee;
    private Button choreDisplay;
    private Chore chore;
    private Person person;

    /**
     * Constructor for ChoreView.
     *
     * @param chore  The chore.
     * @param person The assignee
     */
    public ChoreView(Chore chore, Person person) {
        this(chore, person, false);
    }

    /**
     * Constructor for ChoreView.
     *
     * @param chore  The chore.
     * @param person The assignee
     */
    public ChoreView(Chore chore, Person person, Boolean weekChore) {
        this.person = person;
        this.chore = chore;

        this.choreDisplay = new Button();

        this.choreDisplay.setOnAction(e -> {
            App.setChorePopupScene(this.chore, this.person);

        });
        if (ContrastColor.blackText(chore.getColor())) {
            this.choreDisplay.getStyleClass().add("white-text");
        } else {
            this.choreDisplay.getStyleClass().add("black-text");
        }

        this.choreDisplay.getStyleClass().addAll("padding-medium", "border-rounded",
                "on-hover-underline");

        this.choreDisplay.setStyle("-fx-background-color: " + chore.getColor() + ";");

        if (!weekChore) {
            this.assignee = new Label(person.getDisplayName() + ":");
            this.assignee.getStyleClass().clear();
            this.assignee.getStyleClass().addAll("list-assignee", "padding-small", "black-text");
            this.getChildren().add(this.assignee);
            this.choreDisplay.setText(chore.getName());
        } else {
            this.choreDisplay.setText(person.getDisplayName() + ": " + chore.getName());

        }
        this.getChildren().add(this.choreDisplay);

        if (chore.getChecked()) {
            this.choreDisplay.getStyleClass().add("checked");
        } else if (chore.getTimeTo().isBefore(LocalDate.now())) {
            this.choreDisplay.getStyleClass().add("overdue");

        }

    }

    public void updateWidth(double newWidth) {
        double accountForPadding = 15;
        this.setPrefWidth(newWidth);
        this.setMinWidth(newWidth);

        this.choreDisplay.setMinWidth(newWidth - accountForPadding);
        this.choreDisplay.setPrefWidth(newWidth - accountForPadding);

    }

    @Override
    public Node getFxml() {
        return new VBox(this);
    }

    public VBox getContainer() {
        return new VBox(this);
    }

}
