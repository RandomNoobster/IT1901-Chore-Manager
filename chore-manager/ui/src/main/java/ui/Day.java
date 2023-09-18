package ui;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Day {

    private LocalDate date;

    @FXML
    private Button container = new Button();

    public Day(LocalDate date) {
        this.date = date;
        this.container.setText(date.getDayOfWeek().toString() + " - " + Integer.toString(date.getDayOfMonth()));

        // If date = today, assign special class
        if (date.isEqual(LocalDate.now())) {
            //Add special css for today

        }
    }

    public Button getFxml() {
        return this.container;
    }

}
