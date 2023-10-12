package ui;

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

    /**
     * This method is called when the login button is pressed. If the user exists and the login
     * information is correct, the active user is set to this user.
     */
    @FXML
    public void login() {

        String username = this.username.getText();
        String password = this.password.getText();

        for (Person person : Storage.getInstance().getPersonsList()) {
            if (person.getPassword().getPasswordString().equals(password)
                    && person.getUsername().equals(username)) {
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
