package io.polygonal.verifytask.verifiers

import io.polygonal.plugin.ConditionException
import io.polygonal.verifytask.dto.ObjectType
import io.polygonal.verifytask.dto.PackageInformation
import io.polygonal.verifytask.ports.PackageParser
import spock.lang.Specification
import spock.lang.Unroll

class KotlinSinglePackageVerifierTest extends Specification {

    def packageParser = null as PackageParser
    def verifier = null as BasicSinglePackageVerifier
    def file = null as File
    def packageInformation = null as PackageInformation
    def error = null

    void setup() {
        packageParser = Mock(PackageParser)
        verifier = new KotlinSinglePackageVerifier(packageParser)
        file = GroovyMock(File, constructorArgs: ["./d.txt"])
        packageInformation = new PackageInformation()
        packageParser.parseDirectory(_) >> packageInformation
        error = null
    }

    @Unroll
    def "should verify internalScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
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
        def packageDef = new io.polygonal.plugin.PackageDef('test')
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
        def packageDef = new io.polygonal.plugin.PackageDef('test')
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
