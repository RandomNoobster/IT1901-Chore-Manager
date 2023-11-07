package ui;

import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import ui.dataAccessLayer.DataAccess;

public class CreateCollectiveController {

    private DataAccess dataAccess;

    @FXML
    private TextField name;

    private Integer minLength = 5;

    @FXML
    public void initialize() {
        this.dataAccess = App.getDataAccess();
    }

    private void showAlertWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    @FXML
    public void create() {

        if (this.name.getText().length() > this.minLength) {
            Collective newCollective = new Collective(this.name.getText());
            if (this.dataAccess.addCollective(newCollective)) {
                Person loggedInUser = this.dataAccess.getLoggedInUser();
                this.dataAccess.addPerson(loggedInUser, newCollective.getJoinCode());

                this.dataAccess.logIn(loggedInUser, loggedInUser.getPassword(), newCollective);
                App.switchScene("App");
            } else {
                String error = "Please inform developers if this error occurs,\nbecause it really should not occur...";
                this.showAlertWarning("Unknown error", error);
            }
        } else {
            String error = "Name has to be longer than " + (this.minLength - 1) + " characters";
            this.showAlertWarning("Name not long enough", error);
        }
    }

    @FXML
    public void goToJoin() {
        App.switchScene("JoinCollective");
    }
}
