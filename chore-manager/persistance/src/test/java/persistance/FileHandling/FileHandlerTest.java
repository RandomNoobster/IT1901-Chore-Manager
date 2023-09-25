package persistance.FileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class FileHandlerTest {

    private FileHandler fileHandler;
    private final String fileName = "chore-manager-test-filehandler.json";

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
        map.put("name", "testName");
        JSONObject jsonObject = new JSONObject(map);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        this.fileHandler.writeToFile(jsonArray);

        assertEquals(jsonArray, this.fileHandler.readJSONFile());
    }

    @Test
    public void testReadJSONFileEmpty() {
        assertEquals(0, this.fileHandler.getFile().length());

        assertEquals(new JSONArray(), this.fileHandler.readJSONFile());
    }

    @Test
    public void testReadJSONFileInvalid() {
        assertEquals(0, this.fileHandler.getFile().length());

        this.fileHandler.writeToFile("abc");
        assertEquals(null, this.fileHandler.readJSONFile()); // Catches ParseException and returns null
    }

    @Test
    public void deleteFileTest() {
        assertTrue(this.fileHandler.getFile().exists());
        this.fileHandler.deleteFile();
        assertFalse(this.fileHandler.getFile().exists());
    }

}
