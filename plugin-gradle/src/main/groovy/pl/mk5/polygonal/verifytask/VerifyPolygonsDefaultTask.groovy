package pl.mk5.polygonal.verifytask

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.PolygonExtension
import pl.mk5.polygonal.plugin.PolygonalArchitectureExtension

import javax.inject.Inject

class VerifyPolygonsDefaultTask extends DefaultTask implements VerifyPolygonsTask {
    @Input
    Project project

    @Input
    PolygonalArchitectureExtension extension

    private final WorkerExecutor workerExecutor

    @Inject
    VerifyPolygonsDefaultTask(Project project,
                              PolygonalArchitectureExtension polygonalArchitectureExtension,
                              WorkerExecutor workerExecutor
    ) {
        super();
        this.project = project
        this.extension = polygonalArchitectureExtension
        this.workerExecutor = workerExecutor
        super.setGroup('verification')
    }

    @TaskAction
    void verifyPolygons() {
        if (extension.polygon == null && extension.polygonTemplate == null) {
            throw new IllegalStateException(Message.POLYGON_OR_TEMPLATE_REQUIRED.toString())
        }
        getLogger().info(Message.CHECKING_POLYGONS.withArgs(project.name))
        def polygonDef = extension.polygon
        def defaultPolygonTemplate = new File(extension.sourcesDir, "src/main/resources/polygon.yml")
        if (extension.polygonTemplate == null && defaultPolygonTemplate.canRead()) {
            extension.polygonTemplate = defaultPolygonTemplate
        }
        if (extension.polygonTemplate != null) {
            def ymlParser = new PackagesYmlParser()
            polygonDef = PolygonExtensionsMerger.merge(ymlParser.parseYml(extension.polygonTemplate), extension.polygon ? extension.polygon : new PolygonExtension())
        }
        new PolygonsVerifier(workerExecutor).verifyAllPolygons(polygonDef, extension)
    }
}
