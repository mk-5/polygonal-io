package pl.mk5.polygonal.e2e.universe.ports;

import java.util.List;

import pl.mk5.polygonal.e2e.universe.dto.Planet;

public interface UniverseExplorer {
    List<Planet> explorePlanets();
}
