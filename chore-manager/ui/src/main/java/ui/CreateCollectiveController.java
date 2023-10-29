package ui;

import core.State;
import core.data.Collective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            if (Storage.getInstance().addCollective(newCollective)) {
                newCollective.addPerson(State.getInstance().getLoggedInUser());
                State.getInstance().setCurrentCollective(newCollective);
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
