package ui;

import java.time.LocalDate;
import java.util.HashMap;

import core.data.Chore;
import core.data.RestrictedPerson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.dataAccessLayer.DataAccess;
import ui.viewClasses.PersonMenuItem;

/**
 * This is the controller for the chore creation view.
 */
public class ChoreCreationController {

    private DataAccess dataAccess;

    @FXML
    private TextField name;

    @FXML
    private Button createButton;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider points;

    @FXML
    private Label pointsDisplay;

    @FXML
    private Slider repeats;

    @FXML
    private Label repeatsDisplay;

    @FXML
    private ComboBox<PersonMenuItem> personsMenu;

    private LocalDate dateFrom = LocalDate.MIN;
    private LocalDate dateTo = LocalDate.MIN;

    private final int CHARACTER_MINIMUM = 5;

    public ChoreCreationController() {
    }

    /**
     * This method is called when the view is loaded. It passes the date range of a chore.
     *
     * @param dateFrom The date from which the chore is valid
     * @param dateTo   The date to which the chore is valid
     */
    public void passData(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    /**
     * This method is called when the view is loaded. It initializes the persons menu with all the
     * people.
     */
    @FXML
    protected void initialize() {
        this.dataAccess = App.getDataAccess();

        HashMap<String, RestrictedPerson> persons = this.dataAccess.getPersons();

        for (RestrictedPerson person : persons.values()) {
            this.personsMenu.getItems().add(new PersonMenuItem(person));
        }
    }

    /**
     * This method is called when the create button is pressed. It creates a new chore with the
     * given parameters. The view is then switched back to the main view.
     */
    public void createChore() {
        String choreName = this.name.getText();

        if (choreName.length() < this.CHARACTER_MINIMUM) {
            App.showAlert("Chore name not long enough", "The name of the chore must be at least "
                    + this.CHARACTER_MINIMUM + " characters", AlertType.WARNING);
            return;
        }

        Integer points = (int) this.points.getValue();
        Color selectedColor = this.colorPicker.getValue();
        int red = (int) (selectedColor.getRed() * 255);
        int green = (int) (selectedColor.getGreen() * 255);
        int blue = (int) (selectedColor.getBlue() * 255);

        PersonMenuItem personMenuItem = (PersonMenuItem) this.personsMenu.getValue();
        if (personMenuItem == null) {
            App.showAlert("Assignee not set", "Assign the task to a member of your collective",
                    AlertType.WARNING);
            return;
        }
        RestrictedPerson person = personMenuItem.getPerson();
        RestrictedPerson creator = this.dataAccess.getLoggedInUser();

        // Format each component with leading zeros if necessary
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);

        for (int i = 0; i < (int) this.repeats.getValue(); i++) {
            Chore chore = new Chore(choreName, this.dateFrom.plusWeeks(i), this.dateTo.plusWeeks(i),
                    false, points, hexColor, creator.getUsername(), person.getUsername());
            this.dataAccess.addChore(chore, person);
        }

        App.switchScene("App");
    }

    /**
     * This method is called when the points slider is changed. It updates the points display.
     */
    @FXML
    public void pointsChanged() {
        this.pointsDisplay.setText("Points: " + (int) this.points.getValue());
    }

    /**
     * This method is called when the repeats slider is changed. It updates the repeats display.
     */
    @FXML
    public void repeatsChanged() {
        if ((int) this.repeats.getValue() == 1) {
            this.repeatsDisplay.setText("Repeats: " + (int) this.repeats.getValue() + " time");
        } else {
            this.repeatsDisplay.setText("Repeats: " + (int) this.repeats.getValue() + " times");

        }
    }

    @FXML
    public void toMain() {
        App.switchScene("App");
    }
}
