package pl.mk5.polygonal

import org.gradle.api.Plugin
import org.gradle.api.Project

class PolygonalArchitecturePlugin implements Plugin<Project> {
    void apply(Project project) {
        new ProjectDispatcher(project).dispatch()
    }
}
