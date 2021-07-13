package io.polygonal.verifytask;

import com.google.inject.Inject;
import io.polygonal.Message;
import io.polygonal.plugin.PackageDef;
import io.polygonal.verifytask.dto.ProjectLanguage;
import io.polygonal.verifytask.ports.PackagesVerifier;
import io.polygonal.verifytask.ports.SinglePackageVerifier;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RecursivePackagesVerifier implements PackagesVerifier {
    private final SinglePackageVerifier singlePackageVerifier;

    @Inject
    RecursivePackagesVerifier(ProjectLanguage projectLanguage, Set<SinglePackageVerifier> verifiers) {
        singlePackageVerifier = verifiers.stream()
                .filter(verifier -> verifier.test(projectLanguage.getLanguage()))
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    @Override
    @SneakyThrows
    public void verify(File basePackageDir, List<PackageDef> defExtensions) {
        if (!basePackageDir.isDirectory()) {
            throw new IllegalArgumentException(Message.BASE_PACKAGE_DOESNT_EXIST.withArgs(basePackageDir.getPath()));
        }
        DefaultDataDecorator.decorateWithDefaultData(defExtensions);
        Map<File, PackageDef> definitionsMap = PackagesIntoMapSplitter.splitPackagesIntoMap(basePackageDir, defExtensions);
        singlePackageVerifier.verify(basePackageDir, definitionsMap.get(basePackageDir));
        Files.list(basePackageDir.toPath()).filter(Files::isDirectory).forEach(path -> {
            PackageDef packageDefExtension = definitionsMap.getOrDefault(path.toFile(), new PackageDef(DirectoryToPackageNameConverter.convertToPackageName(basePackageDir, path.toFile())));
            singlePackageVerifier.verify(path.toFile(), packageDefExtension);
        });
    }
}
