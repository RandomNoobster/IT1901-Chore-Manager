package persistence.fileHandling;

import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfigurator {

    private Properties properties = new Properties();

    public EnvironmentConfigurator() {
        String environment = System.getProperty("env", "development"); // Defaults to
                                                                       // "development"
        String envFileName = ".env." + environment;

        Dotenv dotenv = Dotenv.configure().directory("../../.").filename(envFileName).load();
        String saveFilePath = dotenv.get("SAVE_FILE_PATH");
        String saveFilePathAlt = dotenv.get("SAVE_FILE_PATH_ALT", null);

        this.properties.setProperty("saveFilePath", saveFilePath);
        this.properties.setProperty("saveFilePathAlt", saveFilePathAlt);
    }

    public String getSaveFilePath() {
        return this.properties.getProperty("saveFilePath");
    }

    public String getSaveFilePathAlt() {
        return this.properties.getProperty("saveFilePathAlt");
    }

}
