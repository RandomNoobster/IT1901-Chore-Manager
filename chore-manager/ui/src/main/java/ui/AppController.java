package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;
import core.Data.Week;
import core.FileHandling.JSONConverter;
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
    private Person TEMP_PERSON1 = new Person("TEST_PERS1");

    public AppController() {

    }

    @FXML
    protected void initialize() {
        this.weeks = this.createWeeks();

        for (WeekView week : this.weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }

        // This is for testing File Handling
        Chore chore1 = new Chore("TEST_CHORE", LocalDate.now(), LocalDate.now(), true, 10);
        Chore chore2 = new Chore("TEST_CHORE1", LocalDate.now(), LocalDate.now(), true, 50);
        this.TEMP_PERSON.addChore(chore1);
        this.TEMP_PERSON.addChore(chore2);
        this.TEMP_PERSON1.addChore(chore1);

        JSONConverter jsonConverter = new JSONConverter("data.json");
        jsonConverter.deleteFileContent();
        jsonConverter.writePersonsToJSON(new ArrayList<>(List.of(this.TEMP_PERSON, this.TEMP_PERSON1)));
        List<Person> personList = jsonConverter.getPersonsList();
        for (Person person : personList) {
            System.out.println(person.encodeToJSON());
        }
        // Testing end
    }

    private List<WeekView> createWeeks() {
        List<WeekView> weeks = new ArrayList<>();
        for (int i = this.SHIFT_WEEKS; i < this.NUM_WEEKS + this.SHIFT_WEEKS; i++) {
            weeks.add(new WeekView(new Week(LocalDate.now().plusDays(i * 7))));
        }
        return weeks;
    }

}
