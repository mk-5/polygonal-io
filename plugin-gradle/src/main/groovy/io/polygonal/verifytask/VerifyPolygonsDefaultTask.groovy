package io.polygonal.verifytask

import io.polygonal.DiContainer
import io.polygonal.LanguageRecognizer
import io.polygonal.Message
import io.polygonal.plugin.PolygonalArchitectureExtension
import io.polygonal.verifytask.ports.VerifyPolygonsTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor

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
        DiContainer.initialize(LanguageRecognizer.recognize(project))
        new PolygonsVerifier(workerExecutor, extension).verifyAllPolygons()
    }
}
