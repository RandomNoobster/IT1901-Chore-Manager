package ui;

import core.Data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.FileHandling.Storage;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button create;

    public LoginController() {

    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void login() {

        String name = this.username.getText();
        String password = this.password.getText();

        for (Person person : Storage.getPersons()) {
            if (person.getPassword().getPassword().equals(password) && person.getName().equals(name)) {
                Storage.setUser(person);
                break;
            }
        }

        if (Storage.getUser() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Unknown user");
            alert.setHeaderText("Wrong username or password!");

            alert.show();

        } else {
            App.switchScene("App");
        }

    }

    @FXML
    public void create() {
        App.switchScene("CreateUser");

    }
}
