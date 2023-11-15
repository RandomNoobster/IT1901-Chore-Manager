package ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import core.data.Chore;
import javafx.scene.Parent;

/**
 * TestFX App test
 */
public class AppTest extends BaseTestClass {

    private static final String fxmlFileName = "App.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    /**
     * Get the root node
     * 
     * @return the root node
     */
    public Parent getRootNode() {
        return this.root;
    }

    /**
     * Test that the scene is not null
     */
    @Test
    public void testScene() {
        assertNotNull(this.root.getScene());
    }

    @Test
    public void testSetChoreCreationScene() {
        assertDoesNotThrow(() -> {
            App.setChoreCreationScene(LocalDate.of(2023, 10, 2), LocalDate.of(2023, 10, 3));
        });
    }

    @Test
    public void testSetChorePopupScene() {
        Chore chore = new Chore("Test Chore", LocalDate.of(0, 1, 1), LocalDate.of(0, 1, 1),
                0, "#000000", testPerson.getUsername(), testPerson.getUsername());
        assertDoesNotThrow(() -> {
            App.setChorePopupScene(chore, "test");
        });
    }
}
