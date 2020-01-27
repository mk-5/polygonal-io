package pl.mk5.polygonal.verifytask

import pl.mk5.polygonal.polygons.ObjectType
import pl.mk5.polygonal.polygons.PackageDefExtension
import pl.mk5.polygonal.verifytask.models.PackageInformation
import pl.mk5.polygonal.verifytask.parsers.JavaPackageParser
import pl.mk5.polygonal.verifytask.parsers.PackageParser
import spock.lang.Specification
import spock.lang.Unroll

class PackageVerifierTest extends Specification {

    def packageParser = null as PackageParser
    def verifier = null as PackageVerifier
    def file = null as File
    def packageInformation = null as PackageInformation
    def error = null

    void setup() {
        packageParser = Mock(JavaPackageParser)
        verifier = new PackageVerifier(packageParser)
        file = GroovyMock(File, constructorArgs: ["./d.txt"])
        packageInformation = new PackageInformation()
        packageParser.parse(_) >> packageInformation
        error = null
    }

    @Unroll
    def "should verify required attribute"(boolean dirExists, boolean dirRequired, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.required = dirRequired
        file.isDirectory() >> dirExists

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
        dirExists | dirRequired || errorThrown
        true      | true        || null
        false     | true        || ConditionException
        true      | false       || null
    }

    @Unroll
    def "should verify publicScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.publicScope = allowed
        file.isDirectory() >> true
        packageInformation.publicObjects = objects

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

    def "should verify packagePrivateScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.packagePrivateScope = allowed
        file.isDirectory() >> true
        packageInformation.packagePrivateObjects = objects

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

    def "should verify protectedScope attribute"(int objects, int allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.protectedScope = allowed
        file.isDirectory() >> true
        packageInformation.protectedObjects = objects

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

    def "should verify interfaces attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.interfaces = objects

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
        objects | allowed                || errorThrown
        1       | []                     || ConditionException
        0       | []                     || null
        40      | [ObjectType.INTERFACE] || null
    }

    def "should verify classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.classes = objects

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
        objects | allowed            || errorThrown
        1       | []                 || ConditionException
        0       | []                 || null
        40      | [ObjectType.CLASS] || null
    }

    def "should verify abstract classes attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.abstractClasses = objects

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
        objects | allowed                     || errorThrown
        1       | []                          || ConditionException
        0       | []                          || null
        40      | [ObjectType.ABSTRACT_CLASS] || null
    }

    def "should verify enums attribute"(int objects, Set<String> allowed, Class errorThrown) {
        given:
        def packageDef = new PackageDefExtension('test')
        packageDef.types = allowed
        file.isDirectory() >> true
        packageInformation.enums = objects

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
        objects | allowed           || errorThrown
        1       | []                || ConditionException
        0       | []                || null
        40      | [ObjectType.ENUM] || null
    }
}
