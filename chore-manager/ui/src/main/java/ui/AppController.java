package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;
import core.Data.Week;
import core.FileHandling.Storage;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ui.ViewClasses.DayView;
import ui.ViewClasses.WeekView;

public class AppController {

    @FXML
    private VBox weekContainer;

    private List<WeekView> weeks = new ArrayList<>();
    private final int SHIFT_WEEKS = -1; // Number of weeks to shift (example how many weeks before current week)
    private final int NUM_WEEKS = 5; // Number of weeks to create

    public AppController() {
    }

    @FXML
    protected void initialize() {
        this.weeks = this.createWeeks();
        this.addDayActions();

        // Uncomment this if you do not have a chore-manager-data file
        // Storage.createTestFile();
        // this.updateFxml();
    }

    private void addDayActions() {
        for (WeekView week : this.weeks) {
            List<DayView> days = week.getDayViews();
            for (DayView day : days) {
                day.getButton().setOnAction(e -> {
                    DayView target = (DayView) e.getTarget();
                    this.createChore(target.getDay().getDate());
                });
            }
        }
    }

    public void createChore(LocalDate date) {
        Chore chore = new Chore("◦ Påminnelseeeeee", date, date, false, 10);
        Person testPerson = Storage.getPersons().get(0);
        testPerson.addChore(chore);
        System.out.println(testPerson.getChores());
        this.updateFxml();
    }

    public void updateFxml() {
        this.weeks.forEach(w -> w.updateFxml());
    }

    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = this.SHIFT_WEEKS; i < this.NUM_WEEKS + this.SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * Week.WEEK_LENGTH))));
        }

        // Draw weeks
        for (WeekView week : weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }
        return weeks;
    }

}
