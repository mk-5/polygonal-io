package io.polygonal.plugin


import org.gradle.util.ConfigureUtil

class PolygonExtension extends Polygon {
    void packageDef(Closure closure) {
        packagesDefs.add(ConfigureUtil.configure(closure, new PackageDef()))
    }
}
