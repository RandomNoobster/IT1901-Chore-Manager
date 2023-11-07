package ui;

import core.State;
import core.data.Collective;
import core.data.Password;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

/**
 * This is the controller for the create user view.
 */
public class CreateUserController {
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

    }

    private Boolean createAccount(String username, String displayName, Password password) {

        if (username.length() < this.allowedUsername) {
            App.showAlert("Username issue",
                    "Username must be at least " + this.allowedUsername + " characters",
                    AlertType.WARNING);
            return false;
        }
        if (displayName.length() < this.allowedDisplayname) {
            App.showAlert("Fullname issue",
                    "Displayname must be at least " + this.allowedDisplayname + " characters",
                    AlertType.WARNING);
            return false;
        }

        if (!password.isLegal()) {
            App.showAlert("Password issue", password.getFixMsg(), AlertType.WARNING);
            return false;
        }

        // TODO: Change Collective join code here
        Collective limboCollective = Storage.getInstance().getEmptyCollective();
        Person newUser = new Person(username, limboCollective, password, displayName);

        if (!Storage.getInstance().addPerson(newUser, limboCollective.getJoinCode())) {
            App.showAlert("Username issue", "Username is not unique", AlertType.WARNING);
            return false;
        }

        return true;
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

        if (this.createAccount(username, displayName, password)) {

            State.getInstance()
                    .setLoggedInUser(Storage.getInstance().getAllPersons().get(username));
            Storage.getInstance().save();

            if (State.getInstance().getCurrentCollective().isLimboCollective()) {
                App.switchScene("JoinCollective");
            } else {
                App.switchScene("App");
            }
        }

    }

    @FXML
    public void toLogin() {
        App.switchScene("Login");
    }

}