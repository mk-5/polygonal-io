package io.polygonal.plugin

import groovy.transform.PackageScope
import groovy.transform.TupleConstructor
import io.polygonal.DiContainer
import io.polygonal.LanguageRecognizer
import io.polygonal.verifytask.VerifyPolygonsDefaultTask
import io.polygonal.verifytask.ports.VerifyPolygonsTask
import org.gradle.api.Project
import org.gradle.api.Task

import java.nio.file.Paths

@PackageScope
@TupleConstructor(includeFields = true)
class ProjectDispatcher {
    private static final String POLYGONAL_ARCHITECTURE = "polygonalArchitecture"
    private static final String VERIFY_POLYGONS = "verifyPolygons"

    private final Project project
    private final PolygonalArchitectureExtension extension
    private final Task task

    ProjectDispatcher(Project project) {
        this.project = project
        this.extension = project.extensions.create(POLYGONAL_ARCHITECTURE, PolygonalArchitectureExtension, project)
        this.task = project.tasks.create(VERIFY_POLYGONS, VerifyPolygonsDefaultTask, project, extension)
    }

    VerifyPolygonsTask dispatch() {
        task.doFirst {
            def language = LanguageRecognizer.recognize(project)
            assert project.sourceSets.main.resources != null
            if (extension.sourcesDir == null) {
                assert project.sourceSets.main[language.name().toLowerCase()] != null
                extension.sourcesDir = new File("${project.sourceSets.main[language.name().toLowerCase()].srcDirs[0]}")
            }
            if (!extension.sourcesDir.isDirectory()) {
                throw new IllegalStateException("given 'sourcesDir' (${extension.sourcesDir}) is not a directory. Please declare valid source directory for project ${project.name}")
            }
            if ( extension.resourcesDir == null && extension.sourcesDir != null) {
                extension.resourcesDir = Paths.get(extension.sourcesDir.absolutePath, "../resources").toFile()
            } else if ( extension.resourcesDir == null && extension.resourcesDir == null ) {
                assert project.sourceSets.main.resources != null
                extension.resourcesDir = new File("${project.sourceSets.main.resources.srcDirs[0]}")
            }
            def defaultPolygonTemplate = Paths.get(extension.resourcesDir.absolutePath, "../resources/polygon.yml").toFile()
            if (extension.polygonTemplate == null && defaultPolygonTemplate.canRead()) {
                extension.polygonTemplate = defaultPolygonTemplate
            }
        }
        project.tasks.build.dependsOn(task)
        return task as VerifyPolygonsTask
    }
}
