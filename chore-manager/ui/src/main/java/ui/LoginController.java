package ui;

import core.Data.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.FileHandling.Storage;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button create;

    private Stage stage;

    public LoginController() {

    }

    @FXML
    public void initialize() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void login() {

        String name = this.username.getText();
        String password = this.password.getText();

        for (Person person : Storage.getPersons()) {
            if (person.getPassword().equals(password) && person.getName().equals(name)) {
                Storage.setuser(person);
                break;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Scene2.fxml"));

            Parent root = loader.load();
            Scene scene2 = new Scene(root);

            AppController controller = loader.getController();
            controller.setStage(this.stage);

            // Use the stage reference to change the scene
            this.stage.setScene(scene2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void create() {

    }
}
