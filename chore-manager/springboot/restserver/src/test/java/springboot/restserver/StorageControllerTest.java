package springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import core.data.Collective;
import core.data.Person;
import core.data.RestrictedCollective;

/**
 * Tests for the {@link StorageController} class. These tests cover various functionalities related
 * to storage, including handling collectives, persons, and operating modes.
 */
@AutoConfigureMockMvc
@ContextConfiguration(classes = { StorageController.class, StorageService.class,
		AppApplication.class })
@WebMvcTest
public class StorageControllerTest extends BaseTestClass {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Tests the retrieval of a collective by its join code.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testGetCollective() throws Exception {
		String joinCode = Collective.LIMBO_COLLECTIVE_JOIN_CODE;
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/storage/collectives/{joinCode}", joinCode)
						.accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
		RestrictedCollective collective = RestrictedCollective.decodeFromJSON(jsonObject);
		assertEquals(joinCode, collective.getJoinCode());
	}

	/**
	 * Tests the addition of a collective.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testAddCollective() throws Exception {
		RestrictedCollective collective = new RestrictedCollective("testCollectiveName");
		JSONObject collectiveJSON = RestrictedCollective.encodeToJSONObject(collective);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/storage/collectives")
						.accept("application/json").contentType("application/json")
						.content(collectiveJSON.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the removal of a nonexistent collective.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testRemoveNonexistentCollective() throws Exception {
		String nonExistentJoinCode = "nonExistentCode";
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/storage/collectives/{joinCode}",
						nonExistentJoinCode))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the movement of a person to another collective.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testMovePersonToAnotherCollective() throws Exception {
		// Set up the initial person and collective
		JSONObject postJSONPerson = new JSONObject();
		postJSONPerson.put("person", Person.encodeToJSONObject(testPerson));
		postJSONPerson.put("joinCode", testPerson.getCollectiveJoinCode());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/storage/persons/{username}", testPerson.getUsername())
						.contentType("application/json").content(postJSONPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Collective newCollective = new Collective("newCollective");
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/storage/collectives")
						.accept("application/json").contentType("application/json")
						.content(RestrictedCollective.encodeToJSONObject(newCollective).toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		// Now, test the move
		JSONObject requestBody = new JSONObject();
		requestBody.put("oldJoinCode", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
		requestBody.put("newJoinCode", newCollective.getJoinCode());
		requestBody.put("password", testPerson.getPassword().getPasswordString());

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.put("/storage/persons/{username}", testPerson.getUsername())
						.contentType("application/json").content(requestBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the movement of a person to another collective with incorrect credentials.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testMovePersonToAnotherCollectiveWithIncorrectCredentials() throws Exception {
		String username = "testUser";
		String oldJoinCode = "oldJoinCode";
		String newJoinCode = "newJoinCode";
		String incorrectPassword = "incorrectPassword";

		JSONObject requestBody = new JSONObject();
		requestBody.put("oldJoinCode", oldJoinCode);
		requestBody.put("newJoinCode", newJoinCode);
		requestBody.put("password", incorrectPassword);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.put("/storage/persons/{username}", username)
						.contentType("application/json").content(requestBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the removal of a collective.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testRemoveCollective() throws Exception {
		// Add a collective to be removed
		String joinCode = "collectiveToRemove";
		String name = "collectiveToRemoveName";
		RestrictedCollective collective = new RestrictedCollective(name, joinCode);
		JSONObject collectiveJSON = RestrictedCollective.encodeToJSONObject(collective);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/storage/collectives")
						.accept("application/json").contentType("application/json")
						.content(collectiveJSON.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Now, test the removal
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/storage/collectives/{joinCode}", joinCode))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the movement of a person to another collective with a nonexistent user.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testMovePersonToAnotherCollectiveWithNonexistentUser() throws Exception {
		String nonExistentUsername = "nonExistentUser";
		String oldJoinCode = "oldJoinCode";
		String newJoinCode = "newJoinCode";
		String password = "password";

		JSONObject requestBody = new JSONObject();
		requestBody.put("oldJoinCode", oldJoinCode);
		requestBody.put("newJoinCode", newJoinCode);
		requestBody.put("password", password);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.put("/storage/persons/{username}", nonExistentUsername)
						.contentType("application/json").content(requestBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}

	/**
	 * Tests the addition and retrieval of a person.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testAddAndGetPerson() throws Exception {
		Person testPerson = new Person("James", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
		JSONObject postJSONPerson = new JSONObject();
		postJSONPerson.put("person", Person.encodeToJSONObject(testPerson));
		postJSONPerson.put("joinCode", testPerson.getCollectiveJoinCode());

		MvcResult postResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/storage/persons/{username}", testPerson.getUsername())
						.contentType("application/json").content(postJSONPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertNotNull(Boolean.parseBoolean(postResult.getResponse().getContentAsString()));

		MvcResult getResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/storage/persons/{username}", testPerson.getUsername())
						.param("password", testPerson.getPassword().getPasswordString())
						.accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertNotNull(getResult.getResponse().getContentAsString());
	}

	/**
	 * Tests entering standard mode.
	 *
	 * @throws Exception if an error occurs during the test
	 */
	@Test
	public void testEnterTestOrStandardMode() throws Exception {
		// Set the initial mode to something other than "test"
		System.setProperty("env", "development");

		// This will not od anything since the api is in test mode
		this.mockMvc.perform(MockMvcRequestBuilders.post("/storage/mode/enter-test-mode"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// Assert as such
		assertEquals("development", System.getProperty("env"));

		// Now we enter standard mode
		this.mockMvc.perform(MockMvcRequestBuilders.post("/storage/mode/enter-standard-mode"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// Verify that the mode is set to "development"
		assertEquals("development", System.getProperty("env"));

		// Now we go back to test mode
		System.setProperty("env", "test");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/storage/mode/enter-test-mode"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals("test", System.getProperty("env"));
	}
}
