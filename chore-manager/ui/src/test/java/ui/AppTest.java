package ui;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.FileHandling.Storage;

/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

    private Parent root;
    private AppController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        this.root = fxmlLoader.load();
        stage.setScene(new Scene(this.root));
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        Storage.getInstance("chore-manager-data-ui-test.json");
    }

    @AfterEach
    public void clearItems() {
        Storage.getInstance().deleteFile();
    }

    public Parent getRootNode() {
        return this.root;
    }

    private void click(String... labels) {
        for (var label : labels) {
            this.clickOn(LabeledMatchers.hasText(label));
        }
    }

    // @ParameterizedTest
    // @MethodSource
    // public void testClicksOperand(String labels, String operandString) {
    //     for (var label : labels.split(" ")) {
    //         this.click(label);
    //     }
    // }

}
