module main.persistance {
    requires transitive json.simple;
    requires transitive main.core;

    exports persistance.FileHandling;
}
