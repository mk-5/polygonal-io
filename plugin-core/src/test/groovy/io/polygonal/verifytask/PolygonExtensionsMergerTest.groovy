package io.polygonal.verifytask


import spock.lang.Specification

class PolygonExtensionsMergerTest extends Specification {

    def "should merge two polygons"() {
        given:
        def polygon1 = new io.polygonal.plugin.Polygon(packagesDefs: [
                new io.polygonal.plugin.PackageDef('test'),
                new io.polygonal.plugin.PackageDef('test2'),
                new io.polygonal.plugin.PackageDef('test3'),
        ])
        def polygon2 = new io.polygonal.plugin.Polygon(packagesDefs: [
                new io.polygonal.plugin.PackageDef('test'),
                new io.polygonal.plugin.PackageDef('test4')
        ])
        polygon2.packagesDefs[0].publicScope = 10

        when:
        def result = PolygonExtensionsMerger.merge(polygon1, polygon2)

        then:
        result.packagesDefs.find({ p -> p.name.equals('test') }).publicScope == 10
        result.packagesDefs.find({ p -> p.name.equals('test4') }) != null
    }
}
