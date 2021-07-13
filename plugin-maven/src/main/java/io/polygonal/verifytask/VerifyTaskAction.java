package io.polygonal.verifytask;

import io.polygonal.DiContainer;
import io.polygonal.LanguageRecognizer;
import io.polygonal.Message;
import io.polygonal.plugin.Polygon;
import io.polygonal.plugin.PolygonalArchitecture;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class VerifyTaskAction implements BiConsumer<PolygonalArchitecture, MavenProject> {

    @Override
    public void accept(PolygonalArchitecture polygonalArchitecture, MavenProject mavenProject) {
        Polygon polygon = new YmlPolygonDefinition(polygonalArchitecture.getPolygonTemplate()).asPolygon();
        File baseDir = new File(polygonalArchitecture.getSourcesDir(),
                polygonalArchitecture.getBasePackage().replace(".", File.separator));
        RecursivePackagesVerifier packagesVerifier = DiContainer.get(RecursivePackagesVerifier.class);
        SystemStreamLog log = new SystemStreamLog();
        Stream.of(Objects.requireNonNull(baseDir.listFiles(File::isDirectory)))
                .parallel()
                .forEach(dir -> {
                    log.info(Message.CHECK_POLYGON.withArgs(dir.getName()));
                    packagesVerifier.verify(dir, polygon.getPackagesDefs());
                });
    }
}
