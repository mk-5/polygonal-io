package pl.mk5.polygonal.verifytask

import pl.mk5.polygonal.plugin.PackageDef
import pl.mk5.polygonal.plugin.Polygon
import spock.lang.Specification

class PolygonExtensionsMergerTest extends Specification {

    def "should merge two polygons"() {
        given:
        def polygon1 = new Polygon(packagesDefs: [
                new PackageDef('test'),
                new PackageDef('test2'),
                new PackageDef('test3'),
        ])
        def polygon2 = new Polygon(packagesDefs: [
                new PackageDef('test'),
                new PackageDef('test4')
        ])
        polygon2.packagesDefs[0].publicScope = 10

        when:
        def result = PolygonExtensionsMerger.merge(polygon1, polygon2)

        then:
        result.packagesDefs.find({ p -> p.name.equals('test') }).publicScope == 10
        result.packagesDefs.find({ p -> p.name.equals('test4') }) != null
    }
}
