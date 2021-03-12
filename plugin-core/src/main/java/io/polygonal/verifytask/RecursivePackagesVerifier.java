package io.polygonal.verifytask;

import io.polygonal.Message;
import io.polygonal.plugin.PackageDef;
import io.polygonal.verifytask.verifiers.PackageVerifier;
import io.polygonal.verifytask.verifiers.PackageVerifierFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

class RecursivePackagesVerifier {

    private static final DefaultDataDecorator defaultDataDecorator = new DefaultDataDecorator();
    private final String language;

    RecursivePackagesVerifier(String language) {
        this.language = language;
    }

    @SneakyThrows
    public void verify(File basePackageDir, List<PackageDef> defExtensions) {
        if (!basePackageDir.isDirectory()) {
            throw new IllegalArgumentException(Message.BASE_PACKAGE_DOESNT_EXIST.withArgs(basePackageDir.getPath()));
        }
        PackageVerifier packageVerifier = PackageVerifierFactory.forLanguage(language);
        defExtensions = defaultDataDecorator.decorate(defExtensions);
        Map<File, PackageDef> definitionsMap = PackagesSplitter.walkAndSplit(basePackageDir, defExtensions);
        packageVerifier.verify(basePackageDir, definitionsMap.get(basePackageDir));
        Files.list(basePackageDir.toPath()).filter(Files::isDirectory).forEach(path -> {
            PackageDef packageDefExtension = definitionsMap.getOrDefault(path.toFile(), new PackageDef(DirectoryToPackageConverter.convert(basePackageDir, path.toFile())));
            packageVerifier.verify(path.toFile(), packageDefExtension);
        });
    }
}
