package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.codehaus.groovy.runtime.InvokerHelper
import pl.mk5.polygonal.polygons.PolygonExtension

@PackageScope
class PolygonExtensionsMerger {

    static PolygonExtension merge(PolygonExtension p1, PolygonExtension p2) {
        p2.packagesDefs.each { p2PackageDef ->
            def p1PackageDef = p1.packagesDefs.find({ pDef -> pDef.name.equals(p2PackageDef.name) })
            if (p1PackageDef != null) {
                InvokerHelper.setProperties(p1PackageDef, p2PackageDef.properties)
            } else {
                p1.packagesDefs.add(p2PackageDef)
            }
        }
        return p1
    }
}
