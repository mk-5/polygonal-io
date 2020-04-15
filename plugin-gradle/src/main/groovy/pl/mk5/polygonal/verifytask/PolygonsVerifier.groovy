package pl.mk5.polygonal.verifytask

import com.google.gson.Gson
import groovy.transform.PackageScope
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.internal.logging.slf4j.DefaultContextAwareTaskLogger
import org.gradle.workers.WorkerExecutor
import pl.mk5.polygonal.LanguageRecognizer
import pl.mk5.polygonal.plugin.Polygon
import pl.mk5.polygonal.plugin.PolygonalArchitectureExtension

@PackageScope
class PolygonsVerifier {
    private static final Logger log = new DefaultContextAwareTaskLogger(Logging.getLogger(VerifyPolygonsDefaultTask))

    private final WorkerExecutor workerExecutor

    PolygonsVerifier(WorkerExecutor workExecutor) {
        this.workerExecutor = workExecutor
    }

    @SuppressWarnings("UnstableApiUsage")
    void verifyAllPolygons(Polygon polygonDef, PolygonalArchitectureExtension extension) {
        def language = LanguageRecognizer.recognize(extension.project)
        def packagesVerifier = new RecursivePackagesVerifier(language)
        def baseDir = new File(extension.sourcesDir, extension.basePackage.replace('.', File.separator))
        def workQueue = workerExecutor.noIsolation()
        // TODO refactor/di container
        VerifyPolygonAction.setPackagesVerifier(packagesVerifier)
        def polygonJson = new Gson().toJson(polygonDef)
        baseDir.eachDir { dir ->
            workQueue.submit(VerifyPolygonAction.class, { parameters ->
                parameters.directoryPath = dir.absolutePath
                parameters.polygonJson = polygonJson
            })
        }
    }
}
