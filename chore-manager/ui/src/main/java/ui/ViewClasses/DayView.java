package ui.ViewClasses;

import java.time.LocalDate;

import core.Data.Day;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class DayView implements ViewInterface {

    private Day day;

    @FXML
    private Button container = new Button();

    public DayView(Day day) {
        this.day = day;
        this.container.setText(
                day.getDate().getDayOfWeek().toString() + " - " + Integer.toString(day.getDate().getDayOfMonth()));
        this.container.getStyleClass().add("dayContainer");

        // If date = today, assign special class
        if (day.getDate().isEqual(LocalDate.now())) {
            this.container.getStyleClass().add("todayContainer");

        }
    }

    public Day getDay() {
        return this.day;
    }

    @Override
    public Node getFxml() {
        return this.container;
    }

}

// @FXML
//     private Button container = new Button();

//     public Day(LocalDate date) {
//         this.date = date;
//         this.container.setText(date.getDayOfWeek().toString() + " - " + Integer.toString(date.getDayOfMonth()));
//         this.container.getStyleClass().add("dayContainer");

//         // If date = today, assign special class
//         if (date.isEqual(LocalDate.now())) {
//             this.container.getStyleClass().add("todayContainer");

//         }
//     }

//     public Button getFxml() {
//         return this.container;
//     }