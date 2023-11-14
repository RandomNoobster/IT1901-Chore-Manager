package springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

/**
 * Tests for the {@link StateController} class. These tests cover various functionalities related to state management,
 * including user login, logout, chores, and collective information.
 */
@AutoConfigureMockMvc
@ContextConfiguration(classes = { StorageController.class, StorageService.class, AppApplication.class,
        StateController.class, StateService.class })
@WebMvcTest
public class StateControllerTest extends BaseTestClass {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Helper method to add a person to the storage.
     *
     * @param testPerson The person to add.
     * @return {@code true} if the addition is successful, {@code false} otherwise.
     * @throws Exception if an error occurs during the test
     */
    private boolean storageAddPerson(Person testPerson) throws Exception {
        JSONObject postJSONPerson = new JSONObject();
        postJSONPerson.put("person", Person.encodeToJSONObject(testPerson));
        postJSONPerson.put("joinCode", testPerson.getCollectiveJoinCode());

        MvcResult postResult = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/storage/persons/{username}", testPerson.getUsername())
                        .contentType("application/json").content(postJSONPerson.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        return Boolean.parseBoolean(postResult.getResponse().getContentAsString());
    }

    /**
     * Helper method to simulate user login and return the result.
     *
     * @param loginInfo The login information.
     * @return The result of the login request.
     * @throws Exception if an error occurs during the test
     */
    private MvcResult stateLogIn(JSONObject loginInfo) throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/state/log-in").contentType("application/json").content(loginInfo.toString()))
                .andExpect(status().isOk()).andReturn();

        return result;
    }

    /**
     * Helper method to simulate adding a chore and return the result.
     *
     * @return The result of the add chore request.
     * @throws Exception if an error occurs during the test
     */
    private MvcResult stateAddChore() throws Exception {
        Chore chore = new Chore("Test Chore", LocalDate.of(0, 1, 1), LocalDate.of(0, 1, 1),
                0, "#000000", testPerson.getUsername(), testPerson.getUsername());
        testPerson.addChore(chore);

        JSONObject postJSONChore = new JSONObject();
        postJSONChore.put("chore", Chore.encodeToJSONObject(chore));
        postJSONChore.put("assignedPerson", testPerson.getUsername());

        MvcResult result = this.mockMvc
                .perform(post("/state/chores/{uuid}", chore.getUUID())
                        .contentType("application/json").content(postJSONChore.toString()))
                .andExpect(status().isOk()).andReturn();

        return result;
    }

    /**
     * Tests user login functionality.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testLogIn() throws Exception {
        this.storageAddPerson(testPerson);

        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());

        // Log in user
        MvcResult result = this.stateLogIn(loginInfo);

        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

        // Verify the user is logged in
        MvcResult getResult = this.mockMvc.perform(get("/state/logged-in-user"))
                .andExpect(status().isOk()).andReturn();

        assertNotEquals("", getResult.getResponse().getContentAsString());
    }

    /**
     * Tests user login with invalid credentials.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testLogInWithInvalidCredentials() throws Exception {
        // Attempting to log in with incorrect credentials
        String username = "nonExistentUser";
        String joinCode = "nonExistentJoinCode";
        String password = "incorrectPassword";

        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", username);
        loginInfo.put("joinCode", joinCode);
        loginInfo.put("password", password);

        MvcResult result = this.stateLogIn(loginInfo);

        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Tests user logout functionality.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testLogOut() throws Exception {
        this.storageAddPerson(testPerson);

        // Log in user
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());
        this.stateLogIn(loginInfo);

        // Assuming a user is logged in
        this.mockMvc.perform(post("/state/log-out")).andExpect(status().isOk());

        // Verify that the user is logged out
        MvcResult result = this.mockMvc.perform(get("/state/logged-in-user"))
                .andExpect(status().isOk()).andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    /**
     * Tests retrieving the current collective information.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetCurrentCollective() throws Exception {
        this.storageAddPerson(testPerson);

        // Log in user
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());
        this.stateLogIn(loginInfo);

        // Assuming a user is logged in
        MvcResult result = this.mockMvc.perform(get("/state/current-collective"))
                .andExpect(status().isOk()).andReturn();

        assertNotEquals("", result.getResponse().getContentAsString());
    }

    /**
     * Tests adding a chore.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddChore() throws Exception {
        this.storageAddPerson(testPerson);

        // Log in user
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());
        this.stateLogIn(loginInfo);

        MvcResult result = this.stateAddChore();

        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Tests removing a chore.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRemoveChore() throws Exception {
        this.storageAddPerson(testPerson);

        // Log in user
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());
        this.stateLogIn(loginInfo);

        this.stateAddChore();
        UUID uuid = testPerson.getChores().get(0).getUUID();

        // Assuming the added chore is removed
        MvcResult removeResult = this.mockMvc.perform(delete("/state/chores/{uuid}", uuid))
                .andExpect(status().isOk()).andReturn();

        assertTrue(Boolean.parseBoolean(removeResult.getResponse().getContentAsString()));
    }

    /**
     * Tests updating the checked status of a chore.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateChoreChecked() throws Exception {
        this.storageAddPerson(testPerson);

        // Log in user
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("username", testPerson.getUsername());
        loginInfo.put("joinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        loginInfo.put("password", testPerson.getPassword().getPasswordString());
        this.stateLogIn(loginInfo);

        this.stateAddChore();
        int idx = testPerson.getChores().size() - 1;
        UUID uuid = testPerson.getChores().get(idx).getUUID();

        // Assuming the added chore is updated
        MvcResult updateResult = this.mockMvc
                .perform(put("/state/chores/{uuid}?checked=true", uuid).contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(Boolean.parseBoolean(updateResult.getResponse().getContentAsString()));
    }

    /**
     * Tests retrieving the list of chores.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetChores() throws Exception {
        // Assuming a user is logged in
        MvcResult result = this.mockMvc.perform(get("/state/chores")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
    }

    /**
     * Tests retrieving the list of persons.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetPersons() throws Exception {
        // Assuming a user is logged in
        MvcResult result = this.mockMvc.perform(get("/state/persons")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
    }
}
