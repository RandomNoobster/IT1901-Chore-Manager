package persistence.fileHandling;

import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * This class is responsible for configuring the environment variables for the application.
 */
public class EnvironmentConfigurator {

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
     * This constructor reads the current environment and gets the corresponding .env file. Then
     * loads it into properties. If no environment is set, it default to 'development'
     */
    public EnvironmentConfigurator() {
        String environment = System.getProperty("env", "development"); // Defaults to
                                                                       // "development"
        String envFileName = ".env." + environment;

        Dotenv dotenv = Dotenv.configure().directory("../../.").filename(envFileName).load();
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
