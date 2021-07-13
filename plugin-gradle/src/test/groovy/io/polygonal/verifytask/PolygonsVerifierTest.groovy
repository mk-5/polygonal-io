package io.polygonal.verifytask

import io.polygonal.DiContainer
import io.polygonal.Language
import io.polygonal.plugin.Polygon
import io.polygonal.plugin.PolygonalArchitectureExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.workers.WorkerExecutor
import spock.lang.Specification

class PolygonsVerifierTest extends Specification {

    def "should execute verifyAllPolygons"() {
        given:
        def polygon = Spy(Polygon)
        def extension = Spy(PolygonalArchitectureExtension)
        def project = Mock(Project)
        def pluginContainer = Mock(PluginContainer)
        extension.project >> project
        extension.sourcesDir >> File.createTempDir()
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
        DiContainer.initialize(Language.JAVA)
        verifier.verifyAllPolygons()

        then:
        notThrown(Exception)
    }
}
