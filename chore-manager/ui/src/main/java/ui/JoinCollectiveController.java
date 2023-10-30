package ui;

import core.State;
import core.data.Collective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

public class JoinCollectiveController {
    @FXML
    private TextField joinCode;

    private void showAlertWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    @FXML
    public void join() {
        Collective joinedCollective = Storage.getInstance().getCollective(this.joinCode.getText());
        if (joinedCollective != null) {
            joinedCollective.addPerson(State.getInstance().getLoggedInUser());
            State.getInstance().setCurrentCollective(joinedCollective);
            App.switchScene("App");
        } else {
            this.showAlertWarning("Wrong code",
                    "This is not a collective's join code.\nAsk your roommate for the code,\nor be the one to create your collective's collective.");
        }
    }

    @FXML
    public void goToCreate() {
        App.switchScene("CreateCollective");
    }

    @FXML
    public void initialize() {

    }
}
