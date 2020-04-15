package pl.mk5.polygonal.verifytask

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.workers.WorkerExecutor
import pl.mk5.polygonal.plugin.Polygon
import pl.mk5.polygonal.plugin.PolygonalArchitectureExtension
import spock.lang.Specification

class PolygonsVerifierTest extends Specification {

    def "should throw exception when no language plugin provided"() {
        given:
        def polygon = Spy(Polygon)
        def extension = Spy(PolygonalArchitectureExtension)
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
        def verifier = new PolygonsVerifier(Mock(WorkerExecutor))

        when:
        verifier.verifyAllPolygons(polygon, extension)

        then:
        thrown(IllegalStateException)
    }
}
