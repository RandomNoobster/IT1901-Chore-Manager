package ui.viewClasses;

import core.data.Chore;
import core.data.ContrastColor;
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
    private Label assigneeLabel;
    private Button choreDisplay;
    private Chore chore;
    private String assignee;

    /**
     * Constructor for ChoreView.
     *
     * @param chore    The chore.
     * @param assignee The assignee
     */
    public ChoreView(Chore chore, String assignee) {
        this.assignee = assignee;
        this.chore = chore;
        this.assigneeLabel = new Label(this.assignee + ":");
        this.assigneeLabel.getStyleClass().clear();

        this.choreDisplay = new Button();
        this.choreDisplay.setText(chore.getName());

        this.choreDisplay.setOnAction(e -> {
            App.setChorePopupScene(this.chore, this.assignee);

        });
        if (ContrastColor.blackText(chore.getColor())) {
            this.choreDisplay.getStyleClass().add("white-text");
        } else {
            this.choreDisplay.getStyleClass().add("black-text");
        }
        this.choreDisplay.getStyleClass().addAll("padding-medium", "border-rounded",
                "on-hover-underline");
        this.assigneeLabel.getStyleClass().addAll("list-assignee", "padding-small");

        this.choreDisplay.setStyle("-fx-background-color: " + chore.getColor() + ";");

        this.container.getChildren().addAll(this.assigneeLabel, this.choreDisplay);

    }

    @Override
    public Node getFxml() {
        return new VBox(this.container);
    }

    public VBox getContainer() {
        return new VBox(this.container);
    }

}
