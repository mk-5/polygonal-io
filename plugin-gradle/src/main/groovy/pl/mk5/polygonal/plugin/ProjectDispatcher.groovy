package pl.mk5.polygonal.plugin

import groovy.transform.PackageScope
import groovy.transform.TupleConstructor
import org.gradle.api.Project
import org.gradle.api.Task
import pl.mk5.polygonal.LanguageRecognizer
import pl.mk5.polygonal.verifytask.VerifyPolygonsDefaultTask
import pl.mk5.polygonal.verifytask.VerifyPolygonsTask

@PackageScope
@TupleConstructor(includeFields = true)
class ProjectDispatcher {
    private final Project project
    private final PolygonalArchitectureExtension extension
    private final Task task

    ProjectDispatcher(Project project) {
        this.project = project
        this.extension = project.extensions.create("polygonalArchitecture", PolygonalArchitectureExtension, project)
        this.task = project.tasks.create("verifyPolygons", VerifyPolygonsDefaultTask, project, extension)
    }

    VerifyPolygonsTask dispatch() {
        task.doFirst {
            if (extension.sourcesDir == null) {
                def language = LanguageRecognizer.recognize(project)
                extension.sourcesDir = new File(project.projectDir, "src/main/${language}")
            }
            if (!extension.sourcesDir.isDirectory()) {
                throw new IllegalStateException("given 'sourcesDir' (${extension.sourcesDir}) is not a directory. Please provide valid source directory for project ${project.name} by using polygonalArchitecture{ sourcesDir = 'xxx' }")
            }
        }
        project.tasks.build.dependsOn(task)
        return task as VerifyPolygonsTask
    }
}
