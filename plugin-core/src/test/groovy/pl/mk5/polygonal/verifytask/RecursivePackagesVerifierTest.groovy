package pl.mk5.polygonal.verifytask

import spock.lang.Specification

class RecursivePackagesVerifierTest extends Specification {

    def "should throw exception when base directory doesnt exists"() {
        given:
        def dir = Mock(File)
        def packagesVerifier = new RecursivePackagesVerifier("java")
        dir.isDirectory() >> false

        when:
        packagesVerifier.verify(dir, [])

        then:
        thrown(IllegalArgumentException)
    }
}
