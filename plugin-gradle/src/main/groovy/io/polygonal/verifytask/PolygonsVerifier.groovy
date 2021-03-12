package io.polygonal.verifytask

import com.google.gson.Gson
import groovy.transform.PackageScope
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.internal.logging.slf4j.DefaultContextAwareTaskLogger
import org.gradle.workers.WorkerExecutor

@PackageScope
class PolygonsVerifier {
    private static final Logger log = new DefaultContextAwareTaskLogger(Logging.getLogger(VerifyPolygonsDefaultTask))

    private final WorkerExecutor workerExecutor
    private final io.polygonal.plugin.Polygon polygonDef;
    private final io.polygonal.plugin.PolygonalArchitectureExtension extension;

    PolygonsVerifier(WorkerExecutor workExecutor, io.polygonal.plugin.PolygonalArchitectureExtension extension) {
        this.workerExecutor = workExecutor
        // TODO refactor / create polygon parser
        if (extension.polygonTemplate != null) {
            def ymlParser = new PackagesYmlParser()
            this.polygonDef = PolygonExtensionsMerger.merge(ymlParser.parseYml(extension.polygonTemplate), extension.polygon ? extension.polygon : new io.polygonal.plugin.PolygonExtension())
        } else {
            this.polygonDef = extension.polygon;
        }
        this.extension = extension;
    }

    @SuppressWarnings("UnstableApiUsage")
    void verifyAllPolygons() {
        def language = io.polygonal.LanguageRecognizer.recognize(extension.project)
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
