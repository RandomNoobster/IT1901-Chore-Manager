package ui.ViewClasses;

import core.Data.ContrastColor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChoreView implements ViewInterface {
    private VBox container = new VBox();
    private Label assignee;
    private Label chore;

    public ChoreView(String assignee, String chore, String color) {

        this.assignee = new Label(assignee + ":");
        this.assignee.getStyleClass().clear();

        this.assignee.getStyleClass().add("list-assignee");

        this.chore = new Label(chore);
        if (ContrastColor.blackText(color)) {
            this.chore.setTextFill(Color.WHITE);
        } else {
            this.chore.setTextFill(Color.BLACK);
        }
        this.chore.setStyle("-fx-background-color: " + color + ";");

        this.container.getStyleClass().add("list-item");
        this.container.getChildren().addAll(this.assignee, this.chore);

    }

    @Override
    public Node getFxml() {
        return this.container;
    }

    public VBox getContainer() {
        return this.container;
    }

}
