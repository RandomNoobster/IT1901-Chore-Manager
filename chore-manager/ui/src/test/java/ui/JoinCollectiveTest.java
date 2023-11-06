package ui;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

/**
 * Test that the join collective page works as expected.
 */
public class JoinCollectiveTest extends BasicTestClass {

    private static final String fxmlFileName = "JoinCollective.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @Override
    protected void setup() {
        Storage.deleteInstance();
        Storage.getInstance().addCollective(testCollective);
        testPerson.setCollective(null);
        State.getInstance().setLoggedInUser(testPerson);
    }

    /**
     * Test that the user is added to a collective if they enter the correct join code.
     */
    @Test
    public void testJoin() {
        TextField joinCode = this.lookup("#joningCode").query();
        this.interact(() -> {
            joinCode.setText(testCollective.getJoinCode());
        });

        this.clickOn("#joinCollectiveButton");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(State.getInstance().getCurrentCollective().getJoinCode()
                .equals(testCollective.getJoinCode()));
    }

    /**
     * Test that the user does not get added to a collective if they do not enter a join code.
     */
    @Test
    public void testBlankField() {
        this.clickOn("#joinCollectiveButton");

        WaitForAsyncUtils.waitForFxEvents();
        assertNull(State.getInstance().getCurrentCollective());
    }
}
