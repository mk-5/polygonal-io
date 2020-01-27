package pl.mk5.polygonal

import groovy.transform.PackageScope
import groovy.transform.TupleConstructor
import org.gradle.api.Project
import pl.mk5.polygonal.polygons.PolygonalArchitectureExtension
import pl.mk5.polygonal.verifytask.CheckPolygonsTask

@PackageScope
@TupleConstructor(includeFields = true)
class ProjectDispatcher {
    private final Project project

    CheckPolygonsTask dispatch() {
        def extension = project.extensions.create(Const.ROOT_EXTENSION_NAME, PolygonalArchitectureExtension, project)
        def task = project.tasks.create(Const.TASK_CHECK_ARCHITECTURE, CheckPolygonsTask.type, project, extension)
        task.doFirst {
            if (!extension.sourcesDir.isDirectory()) {
                throw new IllegalStateException("given 'sourcesDir' (${extension.sourcesDir}) is not a directory. Please provide valid source directory for project ${project.name} by using polygonalArchitecture{ sourcesDir = 'xxx' }")
            }
        }
        return task
    }
}
