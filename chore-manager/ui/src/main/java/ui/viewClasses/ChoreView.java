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
public class ChoreView implements ViewInterface {
    private VBox container = new VBox();
    private Label assignee;
    private Button choreDisplay;
    private Chore chore;
    private Person person;

    /**
     * Constructor for ChoreView.
     *
     * 
     * @param chore  The chore.
     * @param person The assignee
     */
    public ChoreView(Chore chore, Person person) {
        this.person = person;
        this.chore = chore;
        this.assignee = new Label(person.getDisplayName() + ":");
        this.assignee.getStyleClass().clear();

        this.assignee.getStyleClass().add("list-assignee");

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

        this.choreDisplay.setStyle("-fx-background-color: " + chore.getColor() + ";");

        this.container.getChildren().addAll(this.assignee, this.choreDisplay);

    }

    @Override
    public Node getFxml() {
        return new VBox(this.container);
    }

    public VBox getContainer() {
        return new VBox(this.container);
    }

}
