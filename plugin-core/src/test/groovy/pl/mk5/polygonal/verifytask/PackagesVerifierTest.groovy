package pl.mk5.polygonal.verifytask

import spock.lang.Specification

class PackagesVerifierTest extends Specification {

    def "should throw exception when base directory doesnt exists"() {
        given:
        def dir = Mock(File)
        def packagesVerifier = new PackagesVerifier("java")
        dir.isDirectory() >> false

        when:
        packagesVerifier.verify(dir, [])

        then:
        thrown(IllegalArgumentException)
    }
}
