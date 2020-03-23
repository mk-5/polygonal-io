package pl.mk5.polygonal.verifytask;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.PackageDef;
import pl.mk5.polygonal.verifytask.verifiers.PackageVerifier;
import pl.mk5.polygonal.verifytask.verifiers.PackageVerifierFactory;

class PackagesVerifier {

    private static final DefaultDataDecorator defaultDataDecorator = new DefaultDataDecorator();
    private final String language;

    PackagesVerifier(String language) {
        this.language = language;
    }

    @SneakyThrows
    public void verify(File basePackageDir, List<PackageDef> defExtensions) {
        if (!basePackageDir.isDirectory()) {
            throw new IllegalArgumentException(Message.BASE_PACKAGE_DOESNT_EXIST.withArgs(basePackageDir.getPath()));
        }
        PackageVerifier packageVerifier = PackageVerifierFactory.forLanguage(language);
        defaultDataDecorator.decorate(defExtensions);
        Map<File, PackageDef> definitionsMap = PackagesSplitter.walkAndSplit(basePackageDir, defExtensions);
        Files.list(basePackageDir.toPath()).filter(path -> Files.isDirectory(path)).forEach(path -> {
            PackageDef packageDefExtension = definitionsMap.getOrDefault(path.toFile(), new PackageDef(DirectoryPackageConverter.convert(basePackageDir, path.toFile())));
            packageVerifier.verify(path.toFile(), packageDefExtension);
        });
    }
}
