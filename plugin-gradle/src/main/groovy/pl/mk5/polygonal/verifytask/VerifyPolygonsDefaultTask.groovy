package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.PolygonExtension
import pl.mk5.polygonal.plugin.PolygonalArchitectureExtension

import javax.inject.Inject
import java.util.stream.Stream

/**
 * Checks your polygonal architecture.
 * <p>
 * In case of any problem it tells you what is going wrong.
 */
@PackageScope
class VerifyPolygonsDefaultTask extends DefaultTask implements VerifyPolygonsTask {
    String group = 'verification'

    Project project
    PolygonalArchitectureExtension extension

    @Inject
    VerifyPolygonsDefaultTask(Project project,
                              PolygonalArchitectureExtension polygonalArchitectureExtension) {
        this.project = project
        this.extension = polygonalArchitectureExtension
    }

    @TaskAction
    void verifyPolygons() {
        if (extension.polygon == null && extension.polygonTemplate == null) {
            throw new IllegalStateException(Message.POLYGON_OR_TEMPLATE_REQUIRED.toString())
        }
        getLogger().info(Message.CHECKING_POLYGONS.withArgs(project.name))
        def baseDir = new File(extension.sourcesDir, extension.basePackage.replace('.', File.separator))
        def packagesVerifier = new PackagesVerifier(project)
        def polygonDef = extension.polygon
        if (extension.polygonTemplate != null) {
            def ymlParser = new PackagesYmlParser()
            polygonDef = PolygonExtensionsMerger.merge(ymlParser.parseYml(extension.polygonTemplate), extension.polygon ? extension.polygon : new PolygonExtension())
        }
        Stream.of(baseDir.listFiles({ file -> file.isDirectory() } as FileFilter))
                .parallel()
                .map({ dir ->
                    getLogger().info(Message.CHECK_POLYGON.withArgs(dir.name))
                    packagesVerifier.verify(dir, polygonDef.packagesDefs)
                    return dir
                }).forEach({})
    }
}
