package io.polygonal.verifytask.verifiers


import spock.lang.Specification
import spock.lang.Unroll

class BasicPackageVerifierTest extends Specification {

    def packageParser = null as io.polygonal.verifytask.parsers.PackageParser
    def verifier = null as BasicPackageVerifier
    def file = null as File
    def packageInformation = null as io.polygonal.verifytask.PackageInformation
    def error = null

    void setup() {
        packageParser = Mock(io.polygonal.verifytask.parsers.PackageParser)
        verifier = new BasicPackageVerifier(packageParser)
        file = GroovyMock(File, constructorArgs: ["./d.txt"])
        packageInformation = new io.polygonal.verifytask.PackageInformation()
        packageParser.parse(_) >> packageInformation
        error = null
    }

    @Unroll
    def "should verify required attribute"(boolean dirExists, boolean dirRequired, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.required = dirRequired
        file.isDirectory() >> dirExists

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
        dirExists | dirRequired || errorThrown
        true      | true        || null
        false | true || io.polygonal.verifytask.ConditionException
        true      | false       || null
    }

    @Unroll
    def "should verify publicScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.publicScope = allowed
        file.isDirectory() >> true
        packageInformation.publicObjects = objects

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

    def "should verify packagePrivateScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.packagePrivateScope = allowed
        file.isDirectory() >> true
        packageInformation.packagePrivateObjects = objects

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

    def "should verify protectedScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.protectedScope = allowed
        file.isDirectory() >> true
        packageInformation.protectedObjects = objects

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

    def "should verify interfaces attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.interfaces = objects

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
        objects | allowed                || errorThrown
        1  | []                                         || io.polygonal.verifytask.ConditionException
        0       | []                     || null
        40 | [io.polygonal.plugin.ObjectType.INTERFACE] || null
    }

    def "should verify classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.classes = objects

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
        objects | allowed            || errorThrown
        1  | []                                     || io.polygonal.verifytask.ConditionException
        0       | []                 || null
        40 | [io.polygonal.plugin.ObjectType.CLASS] || null
    }

    def "should verify abstract classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.abstractClasses = objects

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
        objects | allowed                     || errorThrown
        1  | []                                              || io.polygonal.verifytask.ConditionException
        0       | []                          || null
        40 | [io.polygonal.plugin.ObjectType.ABSTRACT_CLASS] || null
    }

    def "should verify enums attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new io.polygonal.plugin.PackageDef('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.enums = objects

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
        objects | allowed           || errorThrown
        1  | []                                    || io.polygonal.verifytask.ConditionException
        0       | []                || null
        40 | [io.polygonal.plugin.ObjectType.ENUM] || null
    }
}
