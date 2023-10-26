package ui;

import java.time.LocalDate;
import java.util.List;

import core.State;
import core.data.Chore;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import persistence.fileHandling.Storage;
import ui.viewClasses.PersonMenuItem;

/**
 * This is the controller for the chore creation view.
 */
public class ChoreCreationController {

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
    private ComboBox<PersonMenuItem> personsMenu;

    private LocalDate dateFrom = LocalDate.MIN;
    private LocalDate dateTo = LocalDate.MIN;

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
        List<Person> persons = State.getInstance().getCurrentCollective().getPersonsList();

        for (Person person : persons) {
            this.personsMenu.getItems().add(new PersonMenuItem(person));
        }
    }

    /**
     * This method is called when the create button is pressed. It creates a new chore with the
     * given parameters. The view is then switched back to the main view.
     */
    public void createChore() {
        String choreName = this.name.getText();
        Integer points = (int) this.points.getValue();
        Color selectedColor = this.colorPicker.getValue();
        int red = (int) (selectedColor.getRed() * 255);
        int green = (int) (selectedColor.getGreen() * 255);
        int blue = (int) (selectedColor.getBlue() * 255);

        PersonMenuItem personMenuItem = (PersonMenuItem) this.personsMenu.getValue();
        if (personMenuItem == null) {
            return;
        }
        Person person = personMenuItem.getPerson();

        // Format each component with leading zeros if necessary
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);
        Chore chore = new Chore(choreName, this.dateFrom, this.dateTo, false, points, hexColor,
                State.getInstance().getLoggedInUser().getUsername());
        State.getInstance().addChore(chore, person);

        Storage.getInstance().save();
        App.switchScene("App");
    }

    /**
     * This method is called when the points slider is changed. It updates the points display.
     */
    @FXML
    public void pointsChanged() {
        this.pointsDisplay.setText("Points: " + (int) this.points.getValue());
    }

    @FXML
    public void toMain() {
        App.switchScene("App");
    }

}
