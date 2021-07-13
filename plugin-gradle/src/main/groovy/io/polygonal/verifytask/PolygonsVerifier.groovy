package io.polygonal.verifytask

import com.google.gson.Gson
import groovy.transform.PackageScope
import io.polygonal.DiContainer
import io.polygonal.LanguageRecognizer
import io.polygonal.plugin.Polygon
import io.polygonal.plugin.PolygonExtension
import io.polygonal.plugin.PolygonalArchitectureExtension
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.internal.logging.slf4j.DefaultContextAwareTaskLogger
import org.gradle.workers.WorkerExecutor

@PackageScope
class PolygonsVerifier {
    private static final Logger log = new DefaultContextAwareTaskLogger(Logging.getLogger(VerifyPolygonsDefaultTask))

    private final WorkerExecutor workerExecutor
    private final Polygon polygonDef;
    private final PolygonalArchitectureExtension extension;

    PolygonsVerifier(WorkerExecutor workExecutor, PolygonalArchitectureExtension extension) {
        this.workerExecutor = workExecutor
        if (extension.polygonTemplate != null) {
            def ymlPolygon = new YmlPolygonDefinition(extension.polygonTemplate)
            this.polygonDef = PolygonExtensionsMerger.merge(ymlPolygon.asPolygon(), extension.polygon ? extension.polygon : new PolygonExtension())
        } else {
            this.polygonDef = extension.polygon;
        }
        this.extension = extension;
    }

    @SuppressWarnings("UnstableApiUsage")
    void verifyAllPolygons() {
        def baseDir = new File(extension.sourcesDir, extension.basePackage.replace('.', File.separator))
        def workQueue = workerExecutor.noIsolation()
        def polygonJson = new Gson().toJson(polygonDef)
        baseDir.eachDir { dir ->
            workQueue.submit(VerifyPolygonAction.class, { parameters ->
                parameters.directoryPath = dir.absolutePath
                parameters.polygonJson = polygonJson
            })
        }
    }
}
