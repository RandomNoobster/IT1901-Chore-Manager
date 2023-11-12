package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

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
}
