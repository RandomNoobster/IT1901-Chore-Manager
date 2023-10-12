package ui.ViewClasses;

import core.Data.ContrastColor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChoreView implements ViewInterface {
    private VBox container = new VBox();
    private Label assagnee;
    private Label chore;

    public ChoreView(String assagnee, String chore, String color) {

        this.assagnee = new Label(assagnee + ":");
        this.assagnee.getStyleClass().clear();

        this.assagnee.getStyleClass().add("list-assagnee");

        this.chore = new Label(chore);
        if (ContrastColor.blackText(color)) {
            this.chore.setTextFill(Color.WHITE);
        } else {
            this.chore.setTextFill(Color.BLACK);
        }
        this.chore.setStyle("-fx-background-color: " + color + ";");

        this.container.getStyleClass().add("list-item");
        this.container.getChildren().addAll(this.assagnee, this.chore);

    }

    @Override
    public Node getFxml() {
        return this.container;
    }

    public VBox getContainer() {
        return this.container;
    }

}
