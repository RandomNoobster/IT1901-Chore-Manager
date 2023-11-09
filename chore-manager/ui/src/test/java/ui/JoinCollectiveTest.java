package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Collective;
import javafx.scene.control.TextField;

/**
 * Test that the join collective page works as expected.
 */
public class JoinCollectiveTest extends BaseTestClass {

    private static final String fxmlFileName = "JoinCollective.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @BeforeEach
    private void boot() {
        testCollective = new Collective("Party Collective");
        dataAccess.addCollective(testCollective);
        dataAccess.logIn(testPerson, testPerson.getPassword(),
                new Collective("Limbo", Collective.LIMBO_COLLECTIVE_JOIN_CODE));
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
        assertTrue(dataAccess.getCurrentCollective().getJoinCode()
                .equals(testCollective.getJoinCode()));
    }

    /**
     * Test that the user does not get added to a collective if they do not enter a join code.
     */
    @Test
    public void testBlankField() {
        this.clickOn("#joinCollectiveButton");

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(dataAccess.getCurrentCollective().getJoinCode(),
                Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    }
}
