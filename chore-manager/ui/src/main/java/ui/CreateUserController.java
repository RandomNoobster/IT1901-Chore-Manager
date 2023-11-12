package ui;

import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import ui.dataAccessLayer.DataAccess;

/**
 * This is the controller for the create user view.
 */
public class CreateUserController {

    private DataAccess dataAccess;

    @FXML
    private TextField username;

    @FXML
    private TextField displayName;

    @FXML
    private PasswordField password;

    @FXML
    private Button createButton;

    private final Integer allowedUsername = 3;
    private final Integer allowedDisplayname = 3;

    public CreateUserController() {

    }

    @FXML
    public void initialize() {
        this.dataAccess = App.getDataAccess();
    }

    private Pair<Boolean, Person> createAccount(String username, String displayName,
            Password password, RestrictedCollective collectiveToJoin) {

        if (username.length() < this.allowedUsername) {
            App.showAlert("Username issue",
                    "Username must be at least " + this.allowedUsername + " characters",
                    AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }
        if (displayName.length() < this.allowedDisplayname) {
            App.showAlert("Fullname issue",
                    "Displayname must be at least " + this.allowedDisplayname + " characters",
                    AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }

        if (!password.isValid()) {
            App.showAlert("Password issue", password.getRequirements(), AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }

        Person newUser = new Person(username, collectiveToJoin.getJoinCode(), password,
                displayName);

        if (!this.dataAccess.addPerson(newUser, collectiveToJoin.getJoinCode())) {
            App.showAlert("Username issue", "Username is not unique", AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }

        return new Pair<Boolean, Person>(true, newUser);
    }

    /**
     * This method is called when the create button is pressed. The view then changes to the Create
     * User view.
     */
    @FXML
    public void create() {
        String username = this.username.getText();
        String displayName = this.displayName.getText();
        Password password = new Password(this.password.getText());
        RestrictedCollective limboCollective = this.dataAccess.getLimboCollective();

        Pair<Boolean, Person> result = this.createAccount(username, displayName, password,
                limboCollective);

        if (result.getKey()) {
            this.dataAccess.logIn(result.getValue(), password, limboCollective);
            App.switchScene("JoinCollective");
        }
    }

    @FXML
    public void toLogin() {
        App.switchScene("Login");
    }

}