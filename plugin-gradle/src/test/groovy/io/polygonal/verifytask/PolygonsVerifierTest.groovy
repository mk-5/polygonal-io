package io.polygonal.verifytask


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.workers.WorkerExecutor
import spock.lang.Specification

class PolygonsVerifierTest extends Specification {

    def "should throw exception when no language plugin provided"() {
        given:
        def polygon = Spy(io.polygonal.plugin.Polygon)
        def extension = Spy(io.polygonal.plugin.PolygonalArchitectureExtension)
        def project = Mock(Project)
        def pluginContainer = Mock(PluginContainer)
        extension.project >> project
        project.plugins >> pluginContainer
        pluginContainer.iterator() >> new Iterator<Plugin>() {
            @Override
            boolean hasNext() {
                return false
            }

            @Override
            Plugin next() {
                return null
            }
        }
        def verifier = new PolygonsVerifier(Mock(WorkerExecutor), extension)

        when:
        verifier.verifyAllPolygons()

        then:
        thrown(IllegalStateException)
    }
}
