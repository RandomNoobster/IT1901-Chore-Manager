module main.persistence {
    requires transitive org.json;
    requires io.github.cdimascio.dotenv.java;
    requires transitive main.core;

    exports persistence.fileHandling;
}
