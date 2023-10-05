package ui;

import java.time.LocalDate;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import persistence.FileHandling.Storage;
import ui.ViewClasses.PersonMenuItem;

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
    private ComboBox personsMenu;

    LocalDate dateFrom;
    LocalDate dateTo;

    public ChoreCreationController() {

    }

    public void passData(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @FXML
    protected void initialize() {

        List<Person> persons = Storage.getPersons();

        for (Person p : persons) {
            this.personsMenu.getItems().add(new PersonMenuItem(p));
        }
    }

    public void createChore() {
        String choreName = this.name.getText();
        Integer points = (int) this.points.getValue();
        Color selectedColor = this.colorPicker.getValue();
        int red = (int) (selectedColor.getRed() * 255);
        int green = (int) (selectedColor.getGreen() * 255);
        int blue = (int) (selectedColor.getBlue() * 255);

        Person person = ((PersonMenuItem) this.personsMenu.getValue()).getPerson();

        // Format each component with leading zeros if necessary
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);
        Chore chore = new Chore(choreName, this.dateFrom, this.dateTo, false, points, hexColor);
        person.addChore(chore);
        App.switchScene("App");
    }

    @FXML
    public void pointsChanged() {
        this.pointsDisplay.setText("Points: " + (int) this.points.getValue());
    }

}
