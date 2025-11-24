package io.polygonal.plugin

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class PolygonExtension extends Polygon {
    PackageDef packageDef(Closure closure) {
        def packageDef = new PackageDef()
        closure.setDelegate(packageDef)
        closure.call()
        packagesDefs.add(packageDef)
        return packageDef
    }
}
