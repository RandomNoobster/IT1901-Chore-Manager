package core.FileHandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * <p>Creates a FileHandler for a specific file</p>
 * <p>If the file does not exist, it will be created</p>
 */
public class FileHandler {

    private File file;
    private final Path PROJECT_PATH = Paths.get("").toAbsolutePath();
    private final String DATA_PATH = "/chore-manager/core/src/main/resources/core/Files";
    private final String RESOURCE_PATH = "/core/Files/";

    /**
     * <p>Creates a FileHandler for a specific file</p>
     * <p>If the file does not exist, it will be created</p>
     * @param fileName The name of the file in ''../resources/core/Files' to read from
     */
    public FileHandler(String fileName) {
        String path = this.RESOURCE_PATH + fileName;
        URL fileUrl = this.getClass().getClassLoader().getResource(path);
        System.out.println(fileUrl.getFile());
        // this.file = new File(fileUrl.getFile());
        try {
            this.file = new File(fileUrl.toURI());
            System.out.println(fileUrl.toURI());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;

        // File c = new File(b.toURI());
        // System.out.println(c.exists());

        System.out.println("File:" + this.file.exists());
    }

    public File getFile() {
        return this.file;
    }

    /**
     * Removes all content in the file associated with this object
     */
    public void deleteFileContent() {
        try {
            new PrintWriter(this.file).close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Writes to the file associated with this object
     * <p>Convert to string:</p>
     * <pre>
     * JSONObject.toJSONString()
     * JSONArray.toJSONString()
     * </pre>
     * @param string The string to append to the file
     */
    public void writeToFile(String string, boolean append) {
        try (FileWriter fileWriter = new FileWriter(this.file, append)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Writes to the file associated with this object
     * Note: This will overwrite the file
     */
    public void writeToFile(String string) {
        this.writeToFile(string, false);
    }

    // Commented out for the moment, since it is very rare that we will write a single object to a file instead of an array of objects
    /**
     * Writes to the file associated with this object
     * Note: This will overwrite the file
     */
    // public void writeToFile(JSONObject jsonObject) {
    //     this.writeToFile(jsonObject.toJSONString());
    // }

    /**
     * Writes to the file associated with this object
     * Note: This will overwrite the file
     */
    public void writeToFile(JSONArray jsonArray) {
        this.writeToFile(jsonArray.toJSONString());
    }

    /**
     * Appends to the file associated with this object
     */
    public void appendToFile(String string) {
        this.writeToFile(string, true);
    }

    /**
     * Appends to the file associated with this object
     */
    // public void appendToFile(JSONObject jsonObject) {
    //     this.writeToFile(jsonObject.toJSONString(), true);
    // }

    /**
     * Appends to the file associated with this object
     */
    public void appendToFile(JSONArray jsonArray) {
        this.writeToFile(jsonArray.toJSONString(), true);
    }

    /** 
     * Reads the file associated with this object
     * @return The JSONObject read from the file
     */
    public JSONArray readJSONFile() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(this.file)) {
            JSONArray jsonObject = (JSONArray) jsonParser.parse(fileReader);
            return jsonObject;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (ParseException e) {
            System.out.println("Error parsing file");
        }
        return null;
    }

    /**
     * NOTE: Deletes the file completely
     * @return True if the file was deleted, false otherwise
     */
    public boolean deleteFile() {
        return this.file.delete();
    }

}