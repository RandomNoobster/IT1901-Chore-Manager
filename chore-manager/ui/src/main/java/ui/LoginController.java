package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button create;

    public LoginController() {
        System.out.println("Test6");
    }

    @FXML
    public void initialize() {
        System.out.println("Test77");

    }
}
