package pl.mk5.polygonal.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PolygonalArchitecturePlugin implements Plugin<Project> {
    void apply(Project project) {
        ProjectDispatcher dispatcher = new ProjectDispatcher(project)
        project.afterEvaluate {
            dispatcher.dispatch()
        }
    }
}
