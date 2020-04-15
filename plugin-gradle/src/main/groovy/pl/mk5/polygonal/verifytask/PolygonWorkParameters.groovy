package pl.mk5.polygonal.verifytask

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

@SuppressWarnings("UnstableApiUsage")
interface PolygonWorkParameters extends WorkParameters, Serializable {
    Property<String> getDirectoryPath()

    Property<String> getPolygonJson()
}
