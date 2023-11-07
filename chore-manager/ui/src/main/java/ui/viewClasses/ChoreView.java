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
        } else if (chore.overdue()) {
            this.choreDisplay.getStyleClass().add("overdue");
        }

    }

    /**
     * Update width of chore
     * 
     * @param newWidth
     */
    public void updateWidth(double newWidth) {
        this.setPrefWidth(newWidth);
        this.setMinWidth(newWidth);

        this.choreDisplay.setMinWidth(newWidth - CSSGlobal.ACCOUNT_FOR_PADDING);
        this.choreDisplay.setPrefWidth(newWidth - CSSGlobal.ACCOUNT_FOR_PADDING);
    }

    @Override
    public Node getFxml() {
        return new VBox(this);
    }

    /**
     * Get the outer container of this chore. For a day-chore this container will contain one node
     * for the assigned persons name, and one for the name of the chore. For a week-chore, only one,
     * containing both the name of the assignee and the task itself
     * 
     * @param newWidth
     */

    public VBox getContainer() {
        return new VBox(this);
    }

}
