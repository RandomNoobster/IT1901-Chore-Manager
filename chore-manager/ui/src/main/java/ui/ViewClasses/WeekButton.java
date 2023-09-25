package ui.ViewClasses;

import core.Data.Week;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class WeekButton extends Button implements ViewInterface {
    Week week;

    public WeekButton(Week week) {
        super();
        this.week = week;
        this.getStyleClass().addAll("weekNumberContainer", "header", "hoverClass");
        this.setText(week.getWeekNumber().toString());

    }

    @Override
    public Node getFxml() {
        return this;
    }

    public Week getWeek() {
        return this.week;
    }

}
