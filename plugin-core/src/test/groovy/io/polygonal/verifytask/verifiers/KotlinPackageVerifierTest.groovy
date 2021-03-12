package io.polygonal.verifytask.verifiers


import spock.lang.Specification
import spock.lang.Unroll

class KotlinPackageVerifierTest extends Specification {

    def packageParser = null as io.polygonal.verifytask.parsers.PackageParser
    def verifier = null as BasicPackageVerifier
    def file = null as File
    def packageInformation = null as io.polygonal.verifytask.PackageInformation
    def error = null

    void setup() {
        packageParser = Mock(io.polygonal.verifytask.parsers.PackageParser)
        verifier = new KotlinPackageVerifier(packageParser)
        file = GroovyMock(File, constructorArgs: ["./d.txt"])
        packageInformation = new io.polygonal.verifytask.PackageInformation()
        packageParser.parse(_) >> packageInformation
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
        } catch (io.polygonal.verifytask.ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed || errorThrown
        1 | 0 || io.polygonal.verifytask.ConditionException
        0       | 0       || null
        40      | -1      || null
        3       | 3       || null
        3 | 2 || io.polygonal.verifytask.ConditionException
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
        } catch (io.polygonal.verifytask.ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed                 || errorThrown
        1  | []                                          || io.polygonal.verifytask.ConditionException
        0       | []                      || null
        40 | [io.polygonal.plugin.ObjectType.DATA_CLASS] || null
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
        } catch (io.polygonal.verifytask.ConditionException e) {
            error = e
        }

        then:
        if (errorThrown != null) {
            assert error != null
            assert error.class == errorThrown
        }

        where:
        objects | allowed                 || errorThrown
        1  | []                                          || io.polygonal.verifytask.ConditionException
        0       | []                      || null
        40 | [io.polygonal.plugin.ObjectType.OPEN_CLASS] || null
    }
}
