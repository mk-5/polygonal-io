package pl.mk5.polygonal.polygons

enum ObjectType {
    INTERFACE,
    CLASS,
    ENUM,
    ABSTRACT_CLASS;

    String lowerCaseName() {
        return name().toLowerCase().replace("_", " ")
    }
}