package pl.mk5.polygonal.verifytask;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.SneakyThrows;
import pl.mk5.polygonal.plugin.PackageDef;

class PackagesSplitter {
    @SneakyThrows
    static Map<File, PackageDef> walkAndSplit(File rootLevel, List<PackageDef> packageDefExtensions) {
        Map<File, PackageDef> defExtensionMap = new HashMap<>();
        if (packageDefExtensions.isEmpty()) {
            return defExtensionMap;
        }
        List<PackageDef> sourcePackageDefExtensions = new ArrayList<>(packageDefExtensions);
        Optional<PackageDef> rootLevelDef = sourcePackageDefExtensions.stream()
                .filter(packageDef -> "".equals(packageDef.getName()))
                .findAny();
        if (rootLevelDef.isPresent()) {
            defExtensionMap.put(rootLevel, rootLevelDef.get());
        }
        Files.walk(rootLevel.toPath())
                .filter(Files::isDirectory)
                .forEach(dir -> {
                    String baseName = DirectoryPackageConverter.convert(rootLevel, dir.toFile());
                    Optional<PackageDef> packageDef = sourcePackageDefExtensions.stream()
                            .filter(p -> baseName.equals(p.getName()))
                            .findAny();
                    if (packageDef.isPresent()) {
                        defExtensionMap.put(dir.toFile(), packageDef.get());
                    }
                });
        return defExtensionMap;
    }
}
