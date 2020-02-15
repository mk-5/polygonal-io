package pl.mk5.polygonal.verifytask.verifiers

import pl.mk5.polygonal.plugin.ObjectType
import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.verifytask.ConditionException
import pl.mk5.polygonal.verifytask.models.PackageInformation
import pl.mk5.polygonal.verifytask.parsers.PackageParser
import spock.lang.Specification
import spock.lang.Unroll

class KotlinPackageVerifierTest extends Specification {

    def packageParser = null as PackageParser
    def verifier = null as BasicPackageVerifier
    def file = null as File
    def packageInformation = null as PackageInformation
    def error = null

    void setup() {
        packageParser = Mock(PackageParser)
        verifier = new KotlinPackageVerifier(packageParser)
        file = GroovyMock(File, constructorArgs: ["./d.txt"])
        packageInformation = new PackageInformation()
        packageParser.parse(_) >> packageInformation
        error = null
    }

    @Unroll
    def "should verify internalScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.internalScope = allowed
        file.isDirectory() >> true
        packageInformation.internalObjects = objects

        when:
        try {
            verifier.verify(file, packageDef)
        } catch (ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed || errorThrown
        1       | 0       || ConditionException
        0       | 0       || null
        40      | -1      || null
        3       | 3       || null
        3       | 2       || ConditionException
    }

    def "should verify data classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.dataClasses = objects

        when:
        try {
            verifier.verify(file, packageDef)
        } catch (ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed                 || errorThrown
        1       | []                      || ConditionException
        0       | []                      || null
        40      | [ObjectType.DATA_CLASS] || null
    }

    def "should verify open classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.openClasses = objects

        when:
        try {
            verifier.verify(file, packageDef)
        } catch (ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed                 || errorThrown
        1       | []                      || ConditionException
        0       | []                      || null
        40      | [ObjectType.OPEN_CLASS] || null
    }
}
