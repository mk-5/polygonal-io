package pl.mk5.polygonal.e2e.universe.models;

import java.beans.ConstructorProperties;

public class Planet {
    private final String name;

    @ConstructorProperties({"name"})
    public Planet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
