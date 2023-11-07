package ui;

import core.State;
import core.data.Collective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

public class CreateCollectiveController {
    @FXML
    private TextField name;

    private Integer minLength = 5;

    @FXML
    public void initialize() {

    }

    @FXML
    public void create() {

        if (this.name.getText().length() > this.minLength) {
            Collective newCollective = new Collective(this.name.getText());
            if (Storage.getInstance().addCollective(newCollective)) {
                newCollective.addPerson(State.getInstance().getLoggedInUser());
                State.getInstance().setCurrentCollective(newCollective);
                Storage.getInstance().save();
                App.switchScene("App");

            } else {
                String error = "Please inform developers if this error occurs,\nbecause it really should not occur...";
                App.showAlert("Unknown error", error, AlertType.WARNING);
            }
        } else {
            String error = "Name has to be longer than " + (this.minLength - 1) + " characters";
            App.showAlert("Name not long enough", error, AlertType.WARNING);
        }
    }

    @FXML
    public void goToJoin() {
        App.switchScene("JoinCollective");
    }
}
