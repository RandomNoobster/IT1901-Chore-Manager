package persistence.fileHandling;

import java.io.File;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * This class is responsible for configuring the environment variables for the application.
 */
public class EnvironmentConfigurator {

    private static final String ROOT_MODULE_DIRECTORY = "chore-manager";
    private Properties properties = new Properties();

    /**
     * This method adds a property to the properties object. If the value is null, it will not be
     * added. Properties extends HashMaps, and therefore do not allow null values
     *
     * @param key   The key of the property
     * @param value The value of the property
     */
    private void addProperty(String key, String value) {
        if (value != null) {
            this.properties.setProperty(key, value);
        }
    }

    /**
     * Returns the absolute path of the root module directory. Assumes the name of the root module
     * directory is `chore-manager` and by definition the root must be above the
     * searchFromDirectory. Recursively searches for the root module directory.
     * 
     * @param searchFromDirectory The directory to start searching from
     * @return The absolute path of the root module directory
     */
    private static String findRootModuleDirectory(String searchFromDirectory) {
        File currentDir = new File(searchFromDirectory);

        if (currentDir.getName().equals(ROOT_MODULE_DIRECTORY)) {
            return currentDir.getAbsolutePath();
        }

        String parentDirectory = currentDir.getParent();
        if (parentDirectory != null) {
            return findRootModuleDirectory(parentDirectory);
        }

        // If no root module directory is found, throw an exception
        throw new RuntimeException("Could not find root module directory");
    }

    /**
     * This constructor reads the current environment and gets the corresponding .env file. Then
     * loads it into properties. If no environment is set, it default to 'development'
     */
    public EnvironmentConfigurator() {
        String environment = System.getProperty("env", "development"); // Defaults to
                                                                       // "development"
        String envFileName = ".env." + environment;
        String rootModuleDirectory = findRootModuleDirectory(System.getProperty("user.dir"));

        Dotenv dotenv = Dotenv.configure().directory(rootModuleDirectory).filename(envFileName)
                .load();
        String saveFilePath = dotenv.get("SAVE_FILE_PATH");
        String saveFilePathAlt = dotenv.get("SAVE_FILE_PATH_ALT", null);

        this.addProperty("saveFilePath", saveFilePath);
        this.addProperty("saveFilePathAlt", saveFilePathAlt);
    }

    public String getSaveFilePath() {
        return this.properties.getProperty("saveFilePath", null);
    }

    public String getSaveFilePathAlt() {
        return this.properties.getProperty("saveFilePathAlt", null);
    }

}
