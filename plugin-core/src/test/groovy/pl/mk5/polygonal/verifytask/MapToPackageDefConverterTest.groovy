package pl.mk5.polygonal.verifytask


import spock.lang.Specification

class MapToPackageDefConverterTest extends Specification {

    def keywordsMap = [
            'publicScope': 'publicScope',
            'name': 'name',
    ]

    def "should convert packages map to packageDefs flat list"() {
        given:
        def converter = new MapToPackageDefConverter()
        def map = [
                'level1': [
                        'publicScope': -1,
                        'level1.1'   : [
                                'level1.1.1': [:]
                        ] as Map,
                        'level1.2'   : [:]
                ],
                'level2': [:]
        ]
        when:
        def resultList = converter.convert(map, keywordsMap)
        println(resultList)

        then:
        resultList.findIndexOf { it -> it.name.equals('level1') } > -1
        resultList.findIndexOf { it -> it.name.equals('level2') } > -1
        resultList.findIndexOf { it -> it.name.equals('level1.level1.1') } > -1
        resultList.findIndexOf { it -> it.name.equals('level1.level1.2') } > -1
        resultList.findIndexOf { it -> it.name.equals('level1.level1.1.level1.1.1') } > -1
    }

    def "should return empty list when no packages"() {
        given:
        def converter = new MapToPackageDefConverter()

        when:
        def resultList = converter.convert([:], keywordsMap)

        then:
        resultList.isEmpty()
    }
}
