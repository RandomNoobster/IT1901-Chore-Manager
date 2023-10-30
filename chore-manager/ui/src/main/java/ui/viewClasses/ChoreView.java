package ui.viewClasses;

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
        this.person = person;
        this.chore = chore;
        this.assignee = new Label(person.getDisplayName() + ":");
        this.assignee.getStyleClass().clear();

        this.choreDisplay = new Button();
        this.choreDisplay.setText(chore.getName());

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
        this.assignee.getStyleClass().addAll("list-assignee", "padding-small");

        this.choreDisplay.setStyle("-fx-background-color: " + chore.getColor() + ";");

        this.getChildren().addAll(this.assignee, this.choreDisplay);
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
        this.choreDisplay.setText(person.getDisplayName() + ": " + chore.getName());

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

        this.getChildren().add(this.choreDisplay);
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
