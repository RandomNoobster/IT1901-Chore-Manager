package persistence.fileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 * <p>
 * Creates a FileHandler for a specific file.
 * </p>
 * <p>
 * If the file does not exist, it will be created.
 * </p>
 */
public class FileHandler {

    private File file;
    private boolean createdNewFile;

    /**
     * <p>
     * Creates a FileHandler for a specific file.
     * </p>
     * <p>
     * If the file does not exist, it will be created.
     * </p>
     *
     * @param fileName The name of the file in home folder to read from.
     */
    public FileHandler(String fileName) {
        Path path = Paths.get(System.getProperty("user.home"), fileName);
        this.file = path.toFile();
        try {
            this.createdNewFile = this.file.createNewFile(); // Creates a new file if it does not
                                                             // exist, else does nothing
        } catch (IOException e) {
            System.out.println("Error creating file");
        }
    }

    public boolean getCreatedNewFile() {
        return this.createdNewFile;
    }

    public File getFile() {
        return this.file;
    }

    /**
     * Removes all content in the file associated with this object.
     */
    public void deleteFileContent() {
        try {
            new FileWriter(this.file, StandardCharsets.UTF_8).close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Writes to the file associated with this object.
     * <p>
     * Convert to string:
     * </p>
     * 
     * <pre>
     * JSONObject.toJSONString()
     * JSONArray.toJSONString()
     * </pre>
     *
     * @param string The string to append to the file
     */
    public void writeToFile(String string, boolean append) {
        try (FileOutputStream fileWriter = new FileOutputStream(this.file, append)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileWriter,
                    StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(string);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Writes to the file associated with this object Note: This will overwrite the file.
     */
    public void writeToFile(String string) {
        this.writeToFile(string, false);
    }

    /**
     * Writes to the file associated with this object Note: This will overwrite the file.
     */
    public void writeToFile(JSONArray jsonArray) {
        this.writeToFile(jsonArray.toString());
    }

    /**
     * Appends to the file associated with this object.
     */
    public void appendToFile(String string) {
        this.writeToFile(string, true);
    }

    /**
     * Appends to the file associated with this object.
     */
    public void appendToFile(JSONArray jsonArray) {
        this.writeToFile(jsonArray.toString(), true);
    }

    /**
     * Reads the file associated with this object.
     *
     * @return The JSONObject read from the file
     */
    public JSONArray readJSONFile() {
        if (this.file.length() == 0) {
            System.out.println(
                    "File is empty, consider using the Storage.fillFileWithDefaultData();");
            return new JSONArray();
        }

        try (FileReader fileReader = new FileReader(this.file, StandardCharsets.UTF_8)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = bufferedReader.lines().collect(Collectors.joining());
            JSONArray jsonArray = new JSONArray(new JSONTokener(content));
            return jsonArray;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (JSONException e) {
            System.out.println("Error parsing JSON");
        }
        return null;
    }

    /**
     * NOTE: Deletes the file completely.
     *
     * @return True if the file was deleted, false otherwise.
     */
    public boolean deleteFile() {
        return this.file.delete();
    }

}