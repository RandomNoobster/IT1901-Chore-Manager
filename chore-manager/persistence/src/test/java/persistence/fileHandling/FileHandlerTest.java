package persistence.fileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {

    private FileHandler fileHandler;
    private final String fileName = "chore-manager-test-filehandler.json";

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

    @BeforeEach
    public void populateFileHandler() {
        this.fileHandler = new FileHandler(this.fileName);
    }

    @AfterEach
    public void deleteFile() {
        this.fileHandler.getFile().delete();
    }

    @Test
    public void testCreateNewFile() {
        assertTrue(this.fileHandler.getCreatedNewFile());

        FileHandler fileHandler2 = new FileHandler(this.fileName);
        assertFalse(fileHandler2.getCreatedNewFile());
    }

    @Test
    public void testGetFile() {
        assertTrue(this.fileHandler.getFile().exists());

        FileHandler fileHandler2 = new FileHandler(this.fileName);
        assertEquals(fileHandler2.getFile(), this.fileHandler.getFile());
    }

    @Test
    public void testWriteToFile() {
        assertEquals(0, this.fileHandler.getFile().length());

        this.fileHandler.writeToFile("abc");
        assertTrue(this.fileHandler.getFile().length() > 0);
    }

    @Test
    public void testDeleteFileContent() {
        assertEquals(0, this.fileHandler.getFile().length());

        this.fileHandler.writeToFile("abc");
        assertTrue(this.fileHandler.getFile().length() > 0);

        this.fileHandler.deleteFileContent();
        assertEquals(0, this.fileHandler.getFile().length());
    }

    @Test
    public void testWriteToFileAppend() {
        assertEquals(0, this.fileHandler.getFile().length());

        this.fileHandler.writeToFile("abc");
        long length = this.fileHandler.getFile().length();
        assertTrue(length > 0);

        this.fileHandler.writeToFile("def", true);
        assertTrue(this.fileHandler.getFile().length() > length);
    }

    @Test
    public void testReadJSONFile() {
        assertEquals(0, this.fileHandler.getFile().length());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", "testName");
        JSONObject jsonObject = new JSONObject(map);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        this.fileHandler.writeToFile(jsonArray);

        assertEquals(jsonArray.toString(), this.fileHandler.readJSONFile().toString());
    }

    @Test
    public void testReadJSONFileEmpty() {
        assertEquals(0, this.fileHandler.getFile().length());

        assertEquals(new JSONArray().toString(), this.fileHandler.readJSONFile().toString());
    }

    @Test
    public void testReadJSONFileInvalid() {
        assertEquals(0, this.fileHandler.getFile().length());

        this.fileHandler.writeToFile("abc");
        assertEquals(null, this.fileHandler.readJSONFile()); // Catches ParseException and returns
                                                             // null
    }

    @Test
    public void deleteFileTest() {
        assertTrue(this.fileHandler.getFile().exists());
        this.fileHandler.deleteFile();
        assertFalse(this.fileHandler.getFile().exists());
    }

}
