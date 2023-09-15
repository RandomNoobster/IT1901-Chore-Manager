package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Data.Person;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AppController {

    @FXML
    private VBox weekContainer;

    private List<Week> weeks = new ArrayList<>();

    // Use this for future functionality for first iteration
    private Person TEMP_PERSON = new Person("TEST_PERS");

    public AppController() {

    }

    @FXML
    protected void initialize() {

        // Add week elements
        for (int i = 1; i >= -3; i--) {
            this.weeks.add(new Week(LocalDate.now().minusDays(i * 7)));
        }

        for (Week week : this.weeks) {
            this.weekContainer.getChildren().add(week.getFxml());
        }

    }

}
