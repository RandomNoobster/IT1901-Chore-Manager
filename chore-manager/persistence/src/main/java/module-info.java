module main.persistence {
    requires transitive json.simple;
    requires io.github.cdimascio.dotenv.java;
    requires transitive main.core;

    exports persistence.fileHandling;
}
