package ui;

import core.data.Chore;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import persistence.fileHandling.Storage;

/**
 * This is the controller for the chore popup view.
 */
public class ChorePopupController {
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
    private Person assignee;

    public ChorePopupController() {

    }

    /**
     * This method is called when the view is loaded. It passes the chore and the assignee.
     *
     * @param chore    The chore
     * @param assignee The assignee
     */
    public void passData(Chore chore, Person assignee) {
        System.out.println(chore);
        this.chore = chore;
        this.assignee = assignee;

        this.choreName.setText(this.chore.getName());
        this.checkbox.setSelected(this.chore.getChecked());
        this.assigneeText.setText("Assignee: " + this.assignee.getDisplayName());
        if (this.chore.overdue()) {
            this.deadline.setText("Deadline: [OVERDUE]");
        } else {
            this.deadline.setText("Deadline: " + this.chore.getTimeTo());
        }
        this.points.setText("Points: " + this.chore.getPoints());
    }

    @FXML
    protected void initialize() {

    }

    @FXML
    public void toMain() {
        App.switchScene("App");
    }

    @FXML
    public void delete() {
        this.assignee.deleteChore(this.chore);
        App.switchScene("App");
        Storage.getInstance().save();
    }

    @FXML
    public void updateChecked() {
        this.chore.setChecked(this.checkbox.isSelected());
        Storage.getInstance().save();
    }

}
