package io.polygonal.verifytask


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
    io.polygonal.plugin.PolygonalArchitectureExtension extension

    private final WorkerExecutor workerExecutor

    @Inject
    VerifyPolygonsDefaultTask(Project project,
                              io.polygonal.plugin.PolygonalArchitectureExtension polygonalArchitectureExtension,
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
            throw new IllegalStateException(io.polygonal.Message.POLYGON_OR_TEMPLATE_REQUIRED.toString())
        }
        getLogger().info(io.polygonal.Message.CHECKING_POLYGONS.withArgs(project.name))
        new PolygonsVerifier(workerExecutor, extension).verifyAllPolygons()
    }
}
