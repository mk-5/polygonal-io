package pl.mk5.polygonal.polygons


import org.gradle.util.ConfigureUtil

class PolygonExtension {
    List<PackageDefExtension> packagesDefs = []

    void packageDef(Closure closure) {
        packagesDefs.add(ConfigureUtil.configure(closure, new PackageDefExtension()))
    }
}
