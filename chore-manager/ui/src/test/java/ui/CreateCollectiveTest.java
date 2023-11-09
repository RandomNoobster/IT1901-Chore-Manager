package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Collective;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

/**
 * Test that the create collective page works as expected.
 */
public class CreateCollectiveTest extends BaseTestClass {

    private TextField collectiveName;
    private Button createButton;
    private Button goBackButton;
    private static final String fxmlFileName = "CreateCollective.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    /**
     * Query the FXML elements.
     */
    @BeforeEach
    public void queryFxml() {
        this.collectiveName = this.lookup("#collectiveNameInput").query();
        this.goBackButton = this.lookup("#goBackButton").query();
        this.createButton = this.lookup("#createButton").query();
    }

    /**
     * Test that the collective gets created if the user enters a valid name.
     */
    @Test
    public void testCreate() throws InterruptedException {
        this.interact(() -> {
            this.collectiveName.setText("Super Duper Collective");
        });

        this.clickOn(this.createButton);

        WaitForAsyncUtils.waitForFxEvents();
        assertNotEquals(testCollective, dataAccess.getCurrentCollective());
    }

    /**
     * Test that the collective does not get created if the user goes back.
     */
    @Test
    public void testBackButton() {
        HashMap<String, Collective> preClick = new HashMap<>(
                Storage.getInstance().getCollectives());

        this.clickOn(this.goBackButton);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getCollectives());
    }
}
