package io.polygonal.verifytask.parsers


import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files

class JavaPackageParserTest extends Specification {

    def parser = new JavaPackageParser() {
        @Override
        io.polygonal.verifytask.PackageInformation parse(File packageDir) {
            def information = new io.polygonal.verifytask.PackageInformation()
            process(Files.newBufferedReader(packageDir.toPath()), information)
            return information
        }
    }

    @Unroll
    def "should get package information #testFile"(String testFile,
                                                   int publicObjects,
                                                   int packagePrivateObjects,
                                                   int protectedObjects,
                                                   int classes,
                                                   int abstractClasses,
                                                   int interfaces,
                                                   int enums) {
        given:
        def file = new File(getClass()
                .getClassLoader()
                .getResource("java/${testFile}")
                .toURI())

        when:
        def information = parser.parse(file)

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
