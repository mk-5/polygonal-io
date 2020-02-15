package pl.mk5.polygonal.e2e.universe;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.mk5.polygonal.e2e.universe.models.Planet;
import pl.mk5.polygonal.e2e.universe.ports.UniverseExplorer;

class SpaceshipUniverseExplorer implements UniverseExplorer {
    @Override
    public List<Planet> explorePlanets() {
        var random = new Random();
        return Stream.generate(() -> new Planet("Planet " + ((char) random.nextInt(26) + 'a')))
                .limit(new Random().nextLong())
                .collect(Collectors.toList());
    }
}
