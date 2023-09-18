package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Person;
import core.Data.Week;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ui.ViewClasses.WeekView;

public class AppController {

    @FXML
    private VBox weekContainer;

    private List<WeekView> weeks = new ArrayList<>();
    private final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks before current week)
    private final int NUM_WEEKS = 5; // Number of weeks to create

    // Use this for future functionality for first iteration
    private Person TEMP_PERSON = new Person("TEST_PERS");

    public AppController() {

    }

    @FXML
    protected void initialize() {
        this.weeks = this.createWeeks();

        for (WeekView week : this.weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }

    }

    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = this.SHIFT_WEEKS; i < this.NUM_WEEKS + this.SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * 7))));
        }
        return weeks;
    }

}
