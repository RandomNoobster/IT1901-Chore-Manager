package ui;

import core.data.Collective;
import core.data.Person;
import javafx.fxml.FXML;
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

    @FXML
    public void create() {
        if (this.name.getText().length() > this.minLength) {
            Collective newCollective = new Collective(this.name.getText());
            if (this.dataAccess.addCollective(newCollective)) {
                Person loggedInUser = this.dataAccess.getLoggedInUser();
                this.dataAccess.addPerson(loggedInUser, newCollective.getJoinCode());
                this.dataAccess.movePersonToAnotherCollective(loggedInUser.getUsername(),
                        loggedInUser.getPassword(), loggedInUser.getCollectiveJoinCode(),
                        newCollective.getJoinCode());

                this.dataAccess.logIn(loggedInUser, loggedInUser.getPassword(), newCollective);
                App.switchScene("App");

            } else {
                String error = "An unexpected error occurred. Please try to reopen the app again.";
                App.showAlert("Unknown error", error, AlertType.WARNING);
            }
        } else {
            String error = "Name has to be longer than " + this.minLength + " characters";
            App.showAlert("Name not long enough", error, AlertType.WARNING);
        }
    }

    @FXML
    public void goToJoin() {
        App.switchScene("JoinCollective");
    }
}
