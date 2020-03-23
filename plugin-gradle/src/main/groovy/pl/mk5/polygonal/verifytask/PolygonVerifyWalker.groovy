package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.internal.logging.slf4j.DefaultContextAwareTaskLogger
import pl.mk5.polygonal.LanguageRecognizer
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.Polygon
import pl.mk5.polygonal.plugin.PolygonalArchitectureExtension

import java.util.stream.Stream

@PackageScope
class PolygonVerifyWalker {
    private static final Logger log = new DefaultContextAwareTaskLogger(Logging.getLogger(VerifyPolygonsDefaultTask))

    static void walkAndVerify(Polygon polygon, PolygonalArchitectureExtension extension) {
        def language = LanguageRecognizer.recognize(extension.project)
        def packagesVerifier = new PackagesVerifier(language)
        def baseDir = new File(extension.sourcesDir, extension.basePackage.replace('.', File.separator))
        Stream.of(baseDir.listFiles({ file -> file.isDirectory() } as FileFilter))
                .parallel()
                .map({ dir ->
                    log.info(Message.CHECK_POLYGON.withArgs(dir.name))
                    packagesVerifier.verify(dir, polygon.packagesDefs)
                    return dir
                }).forEach({})
    }
}
