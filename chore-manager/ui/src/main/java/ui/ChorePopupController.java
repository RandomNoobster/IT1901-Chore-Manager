package ui;

import core.data.Chore;
import core.data.Person;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import ui.dataAccessLayer.DataAccess;

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
    private Person assignee;

    public ChorePopupController() {

    }

    public void passData(Chore chore, Person assignee) {
        System.out.println(chore);
        this.chore = chore;
        this.assignee = assignee;

        this.choreName.setText(this.chore.getName());
        this.checkbox.setSelected(this.chore.getChecked());
        this.assigneeText.setText("Assignee: " + this.assignee.getDisplayName());
        this.deadline.setText("Deadline: " + this.chore.getTimeTo());
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
