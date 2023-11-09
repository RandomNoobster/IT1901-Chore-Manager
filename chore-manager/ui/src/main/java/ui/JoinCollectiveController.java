package ui;

import core.data.Person;
import core.data.RestrictedCollective;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import ui.dataAccessLayer.DataAccess;

/**
 * Controller for the join collective view.
 */
public class JoinCollectiveController {

    private DataAccess dataAccess;

    @FXML
    private TextField joinCode;

    @FXML
    protected void initialize() {
        this.dataAccess = App.getDataAccess();
    }

    /**
     * Joins the user to the collective with the join code entered in the text field. If the join
     * code is valid, the user is moved to the new collective and logged in. If the join code is
     * invalid, a warning message is displayed.
     */
    @FXML
    public void join() {
        System.out.println(this.joinCode.getText());
        RestrictedCollective joinedCollective = this.dataAccess
                .getCollective(this.joinCode.getText());
        if (joinedCollective != null) {
            Person loggedInUser = this.dataAccess.getLoggedInUser();
            this.dataAccess.movePersonToAnotherCollective(loggedInUser.getUsername(),
                    loggedInUser.getPassword(), loggedInUser.getCollectiveJoinCode(),
                    joinedCollective.getJoinCode());

            this.dataAccess.logIn(loggedInUser, loggedInUser.getPassword(), joinedCollective);
            App.switchScene("App");
        } else {
            App.showAlert("Wrong code",
                    "This is not a collective's join code.\nAsk your roommate for the code,\nor be the one to create your collective's collective.",
                    AlertType.WARNING);
        }
    }

    @FXML
    public void goToCreate() {
        App.switchScene("CreateCollective");
    }

}
