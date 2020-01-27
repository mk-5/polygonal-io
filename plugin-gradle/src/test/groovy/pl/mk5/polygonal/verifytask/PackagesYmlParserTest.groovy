package pl.mk5.polygonal.verifytask

import pl.mk5.polygonal.polygons.PackageDefExtension
import spock.lang.Specification

import java.util.stream.Collectors

class PackagesYmlParserTest extends Specification {

    def packageDef = new PackageDefExtension()
    def DEFAULT_PUBLIC = packageDef.publicScope
    def DEFAULT_PACKAGE_PRIVATE = packageDef.packagePrivateScope
    def DEFAULT_PROTECTED = packageDef.protectedScope
    def DEFAULT_TYPES = packageDef.types
    def DEFAULT_REQUIRED = packageDef.required

    def "should parse yml"() {
        given:
        def parser = new PackagesYmlParser()
        def file = new File(getClass()
                .getClassLoader()
                .getResource("polygon.yml")
                .toURI())

        when:
        def polygon = parser.parseYml(file)
        def parsedNames = polygon.packagesDefs.stream().map({ pde -> pde.name }).collect(Collectors.toList())

        then:
        parsedNames.sort() == ['abc', 'abc.defg', 'abc.defg.hijk', 'abc2', 'abc2.cccc', ''].sort()
        def map = polygon.packagesDefs.stream().collect(Collectors.toMap({ p -> p.name }, { p -> p })) as Map<String, PackageDefExtension>
        map[''].publicScope == 0
        map[''].packagePrivateScope == -1
        map[''].protectedScope == 0
        map[''].types.sort() == ['interface', 'class', 'abstract class', 'enum'].sort()
        map['abc'].publicScope == -1
        map['abc'].packagePrivateScope == DEFAULT_PACKAGE_PRIVATE
        map['abc'].types == Collections.singleton('class')
        map['abc.defg'].protectedScope == 5
        map['abc.defg'].publicScope == DEFAULT_PUBLIC
        map['abc.defg'].types.sort() == DEFAULT_TYPES.sort()
        map['abc.defg.hijk'].publicScope == DEFAULT_PUBLIC
        map['abc.defg.hijk'].packagePrivateScope == DEFAULT_PACKAGE_PRIVATE
        map['abc.defg.hijk'].types == Collections.singleton('abstract class')
        map['abc2'].publicScope == -1
        map['abc2'].packagePrivateScope == DEFAULT_PACKAGE_PRIVATE
        map['abc2'].types == Collections.singleton('interface')
        map['abc2.cccc'].publicScope == 1
        map['abc2.cccc'].packagePrivateScope == DEFAULT_PACKAGE_PRIVATE
        map['abc2.cccc'].types == DEFAULT_TYPES
    }
}
