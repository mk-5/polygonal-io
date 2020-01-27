package pl.mk5.polygonal.verifytask

import pl.mk5.polygonal.polygons.PackageDefExtension
import pl.mk5.polygonal.polygons.PolygonExtension
import spock.lang.Specification

class PolygonExtensionsMergerTest extends Specification {

    def "should merge two polygons"() {
        given:
        def polygon1 = new PolygonExtension(packagesDefs: [
                new PackageDefExtension('test'),
                new PackageDefExtension('test2'),
                new PackageDefExtension('test3'),
        ])
        def polygon2 = new PolygonExtension(packagesDefs: [
                new PackageDefExtension('test'),
                new PackageDefExtension('test4')
        ])
        polygon2.packagesDefs[0].publicScope = 10

        when:
        def result = PolygonExtensionsMerger.merge(polygon1, polygon2)

        then:
        result.packagesDefs.find({ p -> p.name.equals('test') }).publicScope == 10
        result.packagesDefs.find({ p -> p.name.equals('test4') }) != null
    }
}
