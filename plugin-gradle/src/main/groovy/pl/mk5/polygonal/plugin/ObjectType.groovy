package pl.mk5.polygonal.plugin

enum ObjectType {
    INTERFACE,
    CLASS,
    ENUM,
    ABSTRACT_CLASS,
    // Kotlin
    OPEN_CLASS,
    DATA_CLASS;

    String lowerCaseName() {
        return name().toLowerCase().replace("_", " ")
    }
}