package pl.mk5.polygonal.verifytask.parsers

import pl.mk5.polygonal.verifytask.models.PackageInformation
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files

class KotlinPackageParserTest extends Specification {

    def parser = new KotlinPackageParser() {
        @Override
        PackageInformation parse(File packageDir) {
            def information = new PackageInformation()
            process(Files.newBufferedReader(packageDir.toPath()), information)
            return information
        }
    }

    @Unroll
    def "should get package information #testFile"(String testFile,
                                                   int publicObjects,
                                                   int packagePrivateObjects,
                                                   int protectedObjects,
                                                   int internalObjects,
                                                   int classes,
                                                   int abstractClasses,
                                                   int interfaces,
                                                   int enums,
                                                   int dataClasses,
                                                   int openClasses
    ) {
        given:
        def file = new File(getClass()
                .getClassLoader()
                .getResource("kotlin/${testFile}")
                .toURI())

        when:
        def information = parser.parse(file)

        then:
        information.publicObjects == publicObjects
        information.classes == classes
        information.internalObjects == internalObjects
        information.protectedObjects == protectedObjects
        information.interfaces == interfaces
        information.abstractClasses == abstractClasses
        information.enums == enums
        information.internalObjects == internalObjects

        where:
        testFile                      | publicObjects | packagePrivateObjects | protectedObjects | internalObjects | classes | abstractClasses | interfaces | enums | dataClasses | openClasses
        'open-class.txt'              | 1             | 0                     | 0                | 0               | 0       | 0               | 0          | 0     | 0           | 1
        'data-class.txt'              | 1             | 0                     | 0                | 0               | 0       | 0               | 0          | 0     | 1           | 0
        'internal-enum.txt'           | 0             | 0                     | 0                | 1               | 0       | 0               | 0          | 1     | 0           | 0
        'public-class.txt'            | 1             | 0                     | 0                | 0               | 1       | 0               | 0          | 0     | 0           | 0
        'public-interface.txt'        | 1             | 0                     | 0                | 0               | 0       | 0               | 1          | 0     | 0           | 0
        'internal-abstract-class.txt' | 0             | 0                     | 0                | 1               | 0       | 1               | 0          | 0     | 0           | 0
    }
}
