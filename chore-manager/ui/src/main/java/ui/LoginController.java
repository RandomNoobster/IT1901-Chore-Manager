package ui;

import java.util.HashMap;

import core.State;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

/**
 * This is the controller for the login view.
 */
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

    private void showAlertWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    /**
     * This method is called when the login button is pressed. If the user exists and the login
     * information is correct, the active user is set to this user.
     */
    @FXML
    public void login() {
        String username = this.username.getText();
        String password = this.password.getText();

        HashMap<String, Person> allPersons = Storage.getInstance().getAllPersons();
        if (!allPersons.containsKey(username)
                || !allPersons.get(username).getPassword().getPasswordString().equals(password)) {
            this.showAlertWarning("Unknown user", "Wrong username or password!");
            return;
        }

        State.getInstance().setLoggedInUser(allPersons.get(username));

        if (State.getInstance().getCurrentCollective().isLimboCollective()) {
            App.switchScene("JoinCollective");
        } else {
            App.switchScene("App");
        }
    }

    @FXML
    public void create() {
        App.switchScene("CreateUser");

    }
}
