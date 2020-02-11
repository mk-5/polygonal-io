package pl.mk5.polygonal.verifytask


import groovy.transform.PackageScope
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.polygons.PolygonalArchitectureExtension
import pl.mk5.polygonal.verifytask.parsers.PackageParserFactory

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
    VerifyPolygonsDefaultTask(Project project, PolygonalArchitectureExtension polygonalArchitectureExtension) {
        this.project = project
        this.extension = polygonalArchitectureExtension
    }

    @TaskAction
    void verifyPolygons() {
        if (extension.polygon == null && extension.polygonTemplate == null) {
            throw new IllegalStateException(Message.POLYGON_OR_TEMPLATE_REQUIRED.toString())
        }
        getLogger().quiet(Message.CHECKING_POLYGONS.withArgs(project.name))
        def baseDir = new File(extension.sourcesDir, extension.basePackage.replace('.', File.separator))
        def packageParser = new PackageParserFactory().forLanguage(PackageParserFactory.JAVA)
        def packageDefsVerifier = new PackagesVerifier(packageParser)
        if (extension.polygonTemplate != null) {
            def ymlParser = new PackagesYmlParser()
            def polygonExtension = ymlParser.parseYml(extension.polygonTemplate)
        }
        Stream.of(baseDir.listFiles({ file -> file.isDirectory() } as FileFilter))
                .parallel()
                .map({ dir ->
                    getLogger().quiet(Message.CHECK_POLYGON.withArgs(dir.name))
                    packageDefsVerifier.verify(dir, extension.polygon.packagesDefs)
                    return dir
                }).forEach({})
    }
}
