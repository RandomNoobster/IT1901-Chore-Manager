package ui;

import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import core.data.RestrictedPerson;
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

    public CreateUserController() {

    }

    @FXML
    public void initialize() {
        this.dataAccess = App.getDataAccess();
    }

    private Pair<Boolean, Person> createAccount(String username, String displayName,
            String passwordString, RestrictedCollective collectiveToJoin) {

        if (!RestrictedPerson.isValid(username, displayName)) {
            App.showAlert("Naming issue", RestrictedPerson.getRequirements(username, displayName),
                    AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }

        if (!Password.isValid(passwordString)) {
            App.showAlert("Password issue", Password.getRequirements(passwordString),
                    AlertType.WARNING);
            return new Pair<Boolean, Person>(false, null);
        }

        Person newUser = new Person(username, collectiveToJoin.getJoinCode(),
                new Password(passwordString), displayName);

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
        String passwordString = this.password.getText();
        RestrictedCollective limboCollective = this.dataAccess.getLimboCollective();

        Pair<Boolean, Person> result = this.createAccount(username, displayName, passwordString,
                limboCollective);

        if (result.getKey()) {
            Password password = new Password(passwordString);
            this.dataAccess.logIn(result.getValue(), password, limboCollective);
            App.switchScene("JoinCollective");
        }
    }

    @FXML
    public void toLogin() {
        App.switchScene("Login");
    }

}