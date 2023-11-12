package ui;

import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.dataAccessLayer.DataAccess;

/**
 * This is the controller for the login view.
 */
public class LoginController {

    private DataAccess dataAccess;

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
        this.dataAccess = App.getDataAccess();
    }

    /**
     * This method is called when the login button is pressed. If the user exists and the login
     * information is correct, the active user is set to this user.
     */
    @FXML
    public void login() {
        String username = this.username.getText();
        String password = this.password.getText();

        if (!Password.isValid(password)) {
            App.showAlert("Unknown user", "Wrong username or password!", AlertType.WARNING);
            return;
        }

        Person user = this.dataAccess.getPerson(username, new Password(password));
        if (user == null) {
            App.showAlert("Unknown user", "Wrong username or password!", AlertType.WARNING);
            return;
        }

        RestrictedCollective collective = this.dataAccess
                .getCollective(user.getCollectiveJoinCode());
        boolean success = this.dataAccess.logIn(user, user.getPassword(), collective);

        if (!success) {
            App.showAlert("Something went wrong", "Something went wrong. Please try again.",
                    AlertType.WARNING);
            return;
        }

        if (collective.isLimboCollective()) {
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
