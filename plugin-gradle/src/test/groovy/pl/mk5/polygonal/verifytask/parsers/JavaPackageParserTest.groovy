package pl.mk5.polygonal.verifytask.parsers

import spock.lang.Specification
import spock.lang.Unroll

class JavaPackageParserTest extends Specification {

    def parser = new JavaPackageParser()
    def rootFile = null as File
    def file = null as File

    void setup() {
        rootFile = GroovyMock(File, constructorArgs: ["./"])
        rootFile.eachFile(_, _) >> { args ->
            args[1](file)
        }
    }

    @Unroll
    def "should get package information #testFile"(String testFile, int publicObjects, int packagePrivateObjects, int protectedObjects, int classes, int abstractClasses, int interfaces, int enums) {
        given:
        file = new File(getClass()
                .getClassLoader()
                .getResource("java/${testFile}")
                .toURI())

        when:
        def information = parser.parse(rootFile)

        then:
        information.publicObjects == publicObjects
        information.classes == classes
        information.packagePrivateObjects == packagePrivateObjects
        information.protectedObjects == protectedObjects
        information.interfaces == interfaces
        information.abstractClasses == abstractClasses
        information.enums == enums

        where:
        testFile                     | publicObjects | packagePrivateObjects | protectedObjects | classes | abstractClasses | interfaces | enums
        'public-class.txt'           | 1             | 0                     | 0                | 1       | 0               | 0          | 0
        'public-interface.txt'       | 1             | 0                     | 0                | 0       | 0               | 1          | 0
        'package-enum.txt'           | 0             | 1                     | 0                | 0       | 0               | 0          | 1
        'protected-interface.txt'    | 0             | 0                     | 1                | 0       | 0               | 1          | 0
        'package-abstract-class.txt' | 0             | 1                     | 0                | 0       | 1               | 0          | 0
    }
}
