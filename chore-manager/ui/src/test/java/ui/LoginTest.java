package ui;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

public class LoginTest extends BasicTestClass {

    private static final String fxmlFileName = "Login.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @Override
    @BeforeAll
    protected void setup() {
        Storage.deleteInstance();
        Storage.getInstance().addCollective(testCollective);
        testPerson.setCollective(testCollective);
        Storage.getInstance().addPerson(testPerson, testPerson.getCollective().getJoinCode());
        State.getInstance().logOutUser();
    }

    /**
     * Test that the user gets logged in if they enter the correct credentials
     */
    @Test
    public void testLogin() {
        TextField name = this.lookup("#username").query();
        this.interact(() -> {
            name.setText(testPerson.getUsername());
        });

        PasswordField pwd = this.lookup("#password").query();
        this.interact(() -> {
            pwd.setText(testPerson.getPassword().getPasswordString());
        });

        this.clickOn("#login");

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(State.getInstance().getLoggedInUser().getUsername()
                .equals(testPerson.getUsername()));
    }

    /**
     * Test that the user does not get logged in if they do not enter username or password.
     */
    @Test
    public void testBlankFields() {
        this.clickOn("#login");

        WaitForAsyncUtils.waitForFxEvents();
        assertNull(State.getInstance().getLoggedInUser());
    }
}
