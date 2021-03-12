package io.polygonal.plugin

import groovy.transform.PackageScope
import groovy.transform.TupleConstructor
import org.gradle.api.Project
import org.gradle.api.Task

import java.nio.file.Paths

@PackageScope
@TupleConstructor(includeFields = true)
class ProjectDispatcher {
    private final Project project
    private final PolygonalArchitectureExtension extension
    private final Task task

    ProjectDispatcher(Project project) {
        this.project = project
        this.extension = project.extensions.create("polygonalArchitecture", PolygonalArchitectureExtension, project)
        this.task = project.tasks.create("verifyPolygons", io.polygonal.verifytask.VerifyPolygonsDefaultTask, project, extension)
    }

    io.polygonal.verifytask.VerifyPolygonsTask dispatch() {
        task.doFirst {
            if (extension.sourcesDir == null) {
                def language = io.polygonal.LanguageRecognizer.recognize(project)
                extension.sourcesDir = new File(project.projectDir, "src/main/${language}")
            }
            if (!extension.sourcesDir.isDirectory()) {
                throw new IllegalStateException("given 'sourcesDir' (${extension.sourcesDir}) is not a directory. Please provide valid source directory for project ${project.name} by using polygonalArchitecture{ sourcesDir = 'xxx' }")
            }
            def defaultPolygonTemplate = Paths.get(extension.sourcesDir.absolutePath, "../resources/polygon.yml").toFile()
            if (extension.polygonTemplate == null && defaultPolygonTemplate.canRead()) {
                extension.polygonTemplate = defaultPolygonTemplate
            }
        }
        project.tasks.build.dependsOn(task)
        return task as io.polygonal.verifytask.VerifyPolygonsTask
    }
}
