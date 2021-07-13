package io.polygonal.verifytask

import io.polygonal.plugin.PackageDef
import io.polygonal.Language
import io.polygonal.verifytask.dto.PackageInformation
import io.polygonal.verifytask.dto.ProjectLanguage
import io.polygonal.verifytask.ports.SinglePackageVerifier
import spock.lang.Specification

class RecursivePackagesVerifierTest extends Specification {

    def "should throw exception when base directory doesnt exists"() {
        given:
        def dir = Mock(File)
        def packagesVerifier = new RecursivePackagesVerifier(new ProjectLanguage(Language.JAVA), [
                new SinglePackageVerifier() {
                    @Override
                    PackageInformation verify(File packageDir, PackageDef packageDef) {
                        return new PackageInformation()
                    }

                    @Override
                    boolean test(Language... languages) {
                        return true
                    }
                }
        ] as Set)
        dir.isDirectory() >> false

        when:
        packagesVerifier.verify(dir, [])

        then:
        thrown(IllegalArgumentException)
    }
}
