package ui;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.State;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginTest extends BaseTestClass {

    private static final String fxmlFileName = "Login.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @BeforeEach
    private void boot() {
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
     * Test that the user does not get logged in if they enter the wrong credentials
     */
    @Test
    public void testCreate() {
        this.clickOn("#create");
        assertNull(State.getInstance().getLoggedInUser());
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
