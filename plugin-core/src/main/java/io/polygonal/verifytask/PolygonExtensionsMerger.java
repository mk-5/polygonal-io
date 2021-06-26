package io.polygonal.verifytask;

import io.polygonal.plugin.PackageDef;
import io.polygonal.plugin.Polygon;

import java.util.List;
import java.util.Optional;

class PolygonExtensionsMerger {
    private PolygonExtensionsMerger() {
    }

    public static Polygon merge(Polygon p1, Polygon p2) {
        List<PackageDef> p1Packages = p1.getPackagesDefs();
        List<PackageDef> p2Packages = p2.getPackagesDefs();
        for (PackageDef p2PackageDef : p2Packages) {
            Optional<PackageDef> p1PackageDef = p1Packages.stream().filter(p -> p.getName().equals(p2PackageDef.getName())).findAny();
            if (p1PackageDef.isPresent()) {
                ObjectHelper.merge(p2PackageDef, p1PackageDef.get());
                PackageDef ppp = p1PackageDef.get();
            } else {
                p1Packages.add(p2PackageDef);
            }
        }
        return p1;
    }
}
