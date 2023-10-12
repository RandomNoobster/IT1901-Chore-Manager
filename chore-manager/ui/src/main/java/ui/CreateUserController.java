package ui;

import core.Data.Password;
import core.Data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.FileHandling.Storage;

public class CreateUserController {

    @FXML
    private TextField username;

    @FXML
    private TextField displayname;

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

    private void errorMsg(String title, String header) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.show();
    }

    private Boolean createAccount(String username, String displayname, Password password) {

        if (username.length() < this.allowedUsername) {
            this.errorMsg("Username issue",
                    "Username must be at least " + this.allowedUsername + " characters");
            return false;
        }
        if (displayname.length() < this.allowedDisplayname) {
            this.errorMsg("Fullname issue",
                    "Displayname must be at least " + this.allowedDisplayname + " characters");
            return false;
        }

        if (Storage.getInstance().getPersons().containsKey(username)) {
            this.errorMsg("Username issue", "Username is not unique");
            return false;
        }

        if (!password.isLegal()) {
            this.errorMsg("Password issue", password.getFixMsg());
            return false;
        }

        Person newUser = new Person(username, password, displayname);
        Storage.getInstance().addPerson(newUser);
        return true;
    }

    @FXML
    public void create() {
        String username = this.username.getText();
        String displayname = this.displayname.getText();
        Password password = new Password(this.password.getText());

        if (this.createAccount(username, displayname, password)) {
            App.switchScene("Login");

        }

    }

}