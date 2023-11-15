package core.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import core.BaseTestClass;

public class JSONValidatorTest extends BaseTestClass {

    /**
     * Test that {@link JSONValidator#decodeFromJSONString} works as expected.
     */
    @Test
    public void testDecodeFromJSONString() {
        // Test valid input
        assertDoesNotThrow(() -> JSONValidator.decodeFromJSONString(
                "{\"name\":\"Vaske\",\"timeFrom\":\"2021-01-01\",\"timeTo\":\"2021-01-02\",\"isWeekly\":false,\"points\":10,\"color\":\"#FFFFFF\",\"checked\":false,\"daysIncompleted\":0,\"creator\":\"Creator\",\"assignee\":\"Assignee\",\"uuid\":\""
                        + "12345678-1234-1234-1234-123456789012" + "\"}"));

        // Test invalid input
        assertThrows(IllegalArgumentException.class,
                () -> JSONValidator.decodeFromJSONString("test"));
    }

    /**
     * Test that {@link JSONValidator#decodeFromJSONStringArray} works as expected.
     */
    @Test
    public void testDecodeFromJSONStringArray() {
        // Test valid input
        assertDoesNotThrow(() -> JSONValidator.decodeFromJSONStringArray(
                "[{\"name\":\"Vaske\",\"timeFrom\":\"2021-01-01\",\"timeTo\":\"2021-01-02\",\"isWeekly\":false,\"points\":10,\"color\":\"#FFFFFF\",\"checked\":false,\"daysIncompleted\":0,\"creator\":\"Creator\",\"assignee\":\"Assignee\",\"uuid\":\""
                        + "12345678-1234-1234-1234-123456789012" + "\"}]"));

        // Test invalid input
        assertThrows(IllegalArgumentException.class,
                () -> JSONValidator.decodeFromJSONStringArray("test"));
    }
}
