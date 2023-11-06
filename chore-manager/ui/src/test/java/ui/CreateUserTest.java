package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import core.data.Person;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistence.fileHandling.Storage;

/**
 * Test that the create user page works as expected.
 */
public class CreateUserTest extends BasicTestClass {

    private TextField username;
    private TextField displayname;
    private PasswordField password;
    private Button create;
    private Button goBack;
    private static final String fxmlFileName = "CreateUser.fxml";

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    /**
     * Query the FXML elements.
     */
    @BeforeEach
    public void queryFxml() {
        this.username = this.lookup("#username").query();
        this.displayname = this.lookup("#displayName").query();
        this.password = this.lookup("#password").query();
        this.create = this.lookup("#createButton1").query();
        this.goBack = this.lookup("#goBack").query();
    }

    /**
     * Test that the user gets created if they enter the correct credentials
     */
    @Test
    public void testSuccessfulCreate() {
        this.interact(() -> {
            this.username.setText("Ole Petter");
        });

        this.interact(() -> {
            this.displayname.setText("OlePetter69420");
        });

        this.interact(() -> {
            this.password.setText("Password12345");
        });

        this.clickOn(this.create);

        WaitForAsyncUtils.waitForFxEvents();
        assertNotNull(Storage.getInstance().getAllPersons().get("Ole Petter"));
    }

    /**
     * Test that the user does not get created if they enter a username that already exists.
     */
    @Test
    public void testAlreadyExists() {
        this.interact(() -> {
            this.username.setText("Kristoffer");
        });

        this.interact(() -> {
            this.displayname.setText("Kristoffer69420");
        });

        this.interact(() -> {
            this.password.setText("Password12345");
        });

        List<Person> preClick = new ArrayList<>(Storage.getInstance().getAllPersonsList());
        this.clickOn(this.create);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getAllPersonsList());
    }

    /**
     * Test that no user is created if they go back.
     */
    @Test
    public void testBackButton() {
        List<Person> preClick = new ArrayList<>(Storage.getInstance().getAllPersonsList());

        this.clickOn(this.goBack);

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(preClick, Storage.getInstance().getAllPersonsList());
    }
}
