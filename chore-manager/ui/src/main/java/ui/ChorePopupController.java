package ui;

import core.data.Chore;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import ui.dataAccessLayer.DataAccess;

/**
 * Controller for the chore popup window.
 */
public class ChorePopupController {

    private DataAccess dataAccess;

    @FXML
    private Label choreName;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Text assigneeText;

    @FXML
    private Text deadline;

    @FXML
    private Text points;

    private Chore chore;
    private String assignee;

    public ChorePopupController() {

    }

    /**
     * Passes data to the popup window.
     *
     * @param chore    the chore to display
     * @param assignee the assignee of the chore
     */
    public void passData(Chore chore, String assignee) {
        this.chore = new Chore(chore);
        this.assignee = assignee;

        this.choreName.setText(this.chore.getName());
        this.checkbox.setSelected(this.chore.getChecked());
        this.assigneeText.setText("Assignee: " + this.assignee);
        if (this.chore.overdue()) {
            this.deadline.setText("Deadline: [OVERDUE]");
        } else {
            this.deadline.setText("Deadline: " + this.chore.getTimeTo());
        }
        this.points.setText("Points: " + this.chore.getPoints());
    }

    @FXML
    protected void initialize() {
        this.dataAccess = App.getDataAccess();
    }

    @FXML
    public void toMain() {
        App.switchScene("App");
    }

    @FXML
    public void delete() {
        this.dataAccess.removeChore(this.chore);
        App.switchScene("App");
    }

    @FXML
    public void updateChecked() {
        this.dataAccess.updateChoreChecked(this.chore, this.checkbox.isSelected());
    }
}
