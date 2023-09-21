package ui;

import java.io.IOException;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        this.root = fxmlLoader.load();
        stage.setScene(new Scene(this.root));
        stage.show();
    }

    public Parent getRootNode() {
        return this.root;
    }
}
