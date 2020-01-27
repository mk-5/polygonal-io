package pl.mk5.polygonal.polygons


import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

class PolygonalArchitectureExtension {
    /**
     * Sources directory, required
     */
    File sourcesDir
    /**
     * Base package, this is a package for polygon packages
     */
    String basePackage = ''
    /**
     * Polygon definition
     */
    PolygonExtension polygon
    /**
     * Polygon template, can be used instead of {@link #polygon}
     */
    File polygonTemplate
    /**
     * Strict mode means that only packages from definitions are allowed
     */
    boolean strictMode = true

    PolygonalArchitectureExtension(Project project) {
        sourcesDir = new File(project.projectDir, 'src/main/java')
    }

    PolygonExtension polygon(Closure closure) {
        polygon = ConfigureUtil.configure(closure, new PolygonExtension())
        return polygon
    }
}
