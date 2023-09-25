module main.persistence {
    requires transitive json.simple;
    requires transitive main.core;

    exports persistence.FileHandling;
}
