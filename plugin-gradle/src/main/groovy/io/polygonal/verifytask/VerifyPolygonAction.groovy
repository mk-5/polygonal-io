package io.polygonal.verifytask

import com.google.gson.Gson
import groovy.transform.PackageScope
import io.polygonal.Message
import io.polygonal.plugin.Polygon
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.internal.logging.slf4j.DefaultContextAwareTaskLogger
import org.gradle.workers.WorkAction

@PackageScope
@SuppressWarnings("UnstableApiUsage")
abstract class VerifyPolygonAction implements WorkAction<PolygonWorkParameters> {
    private static final Logger log = new DefaultContextAwareTaskLogger(Logging.getLogger(VerifyPolygonsDefaultTask))

    private static RecursivePackagesVerifier packagesVerifier

    static void setPackagesVerifier(RecursivePackagesVerifier packagesVerifier) {
        this.packagesVerifier = packagesVerifier
    }

    @Override
    void execute() {
        def params = getParameters()
        def directory = new File(params.getDirectoryPath().get())
        assert directory.isDirectory()
        assert packagesVerifier != null
        log.info(Message.CHECK_POLYGON.withArgs(directory.name))
        def polygon = new Gson().fromJson(params.getPolygonJson().get(), Polygon)
        packagesVerifier.verify(directory, polygon.packagesDefs)
    }
}
