package io.polygonal.verifytask.dto;

public enum ObjectType {
    INTERFACE,
    CLASS,
    ENUM,
    ABSTRACT_CLASS,
    OPEN_CLASS,
    DATA_CLASS;

    public String lowerCaseName() {
        return name().toLowerCase().replace("_", " ");
    }

}
