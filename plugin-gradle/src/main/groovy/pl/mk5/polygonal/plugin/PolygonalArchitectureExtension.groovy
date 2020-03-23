package pl.mk5.polygonal.plugin

import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

class PolygonalArchitectureExtension extends PolygonalArchitecture {

    private final Project project

    PolygonalArchitectureExtension(Project project) {
        this.project = project
    }

    PolygonExtension polygon(Closure closure) {
        polygon = ConfigureUtil.configure(closure, new PolygonExtension())
        return polygon
    }

    Project getProject() {
        return project
    }
}
