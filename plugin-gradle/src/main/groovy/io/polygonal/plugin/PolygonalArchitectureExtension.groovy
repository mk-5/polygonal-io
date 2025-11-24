package io.polygonal.plugin

import org.gradle.api.Action
import org.gradle.api.Project

class PolygonalArchitectureExtension extends PolygonalArchitecture {

    private final Project project

    PolygonalArchitectureExtension(Project project) {
        this.project = project
    }

    PolygonExtension polygon(Closure closure) {
        polygon = new PolygonExtension()
        closure.setDelegate(polygon)
        closure.call()
        return polygon
    }

    Project getProject() {
        return project
    }
}
